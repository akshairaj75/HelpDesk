package com.backend.helpdeskpro.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.audit.AuditLogResponseDto;
import com.backend.helpdeskpro.dto.audit.RecentActivityDto;
import com.backend.helpdeskpro.entity.AuditLog;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.repository.AuditLogRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditLogRepository auditLogRepository;

    private final ObjectMapper objectMapper;

    public AuditServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    // @Override
    // public List<AuditLogResponseDto> getAllAuditLogs(CustomUserPrincipal
    // authUser) {
    // List<AuditLog> auditLogs = auditLogRepository.findAll();
    // return auditLogs
    // .stream()
    // .map(AuditLogResponseDto::fromEntity)
    // .toList();
    // }

    @Override
    public List<RecentActivityDto> getAllAuditLogs(CustomUserPrincipal authUser) {

        List<AuditLog> auditLogs = auditLogRepository.findAll();
        return auditLogs
                .stream()
                .map(this::mapToRecentActivity)
                .toList();

    }

    @Transactional
    @Override
    public void logAction(
            String entityType,
            Long entityId,
            User performedBy,
            AuditAction action,
            Map<String, Object> payload,
            HttpServletRequest request) {

        AuditLog auditLog = new AuditLog();

        auditLog.setEntityType(entityType);
        auditLog.setEntityId(entityId);
        auditLog.setActor(performedBy);
        auditLog.setAction(action);
        auditLog.setPayload(convertPayloadToJson(payload));
        auditLog.setIpAddress(getClientIpAddress(request));

        auditLogRepository.save(auditLog);
    }

    @Override
    public List<AuditLogResponseDto> getLogsByEntity(String entityType, Long entityId) {

        List<AuditLog> auditLogs = auditLogRepository.findByEntityTypeAndEntityIdOrderByCreatedAtDesc(entityType,
                entityId);
        return auditLogs
                .stream()
                .map(AuditLogResponseDto::fromEntity)
                .toList();
    }

    @Override
    public List<AuditLogResponseDto> getLogsByUser(Long userId) {

        return auditLogRepository.findByActorIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(AuditLogResponseDto::fromEntity)
                .toList();
    }

    private String convertPayloadToJson(Map<String, Object> payload) {

        if (payload == null || payload.isEmpty()) {
            return "{}";
        }

        try {
            return objectMapper.writeValueAsString(payload);
        } catch (JsonProcessingException e) {
            return "{\"error\":\"Failed to convert payload to JSON\"}";
        }
    }

    private String getClientIpAddress(HttpServletRequest request) {

        if (request == null) {
            return null;
        }

        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }

        String realIp = request.getHeader("X-Real-IP");
        if (realIp != null && !realIp.isBlank()) {
            return realIp;
        }

        return request.getRemoteAddr();
    }

    private RecentActivityDto mapToRecentActivity(AuditLog log) {

        RecentActivityDto dto = new RecentActivityDto();

        dto.setAuditLogId(log.getId());
        dto.setCreatedAt(log.getCreatedAt());

        String payload = log.getPayload();

        switch (log.getAction()) {

            case SLA_BREACHED -> {
                dto.setTitle("SLA breach");
                dto.setColor("danger");
                dto.setMessage(
                        readPayloadValue(payload, "ticketNo")
                                + " exceeded SLA");
            }

            case ASSIGNED -> {
                dto.setTitle("Assigned");
                dto.setColor("primary");
                dto.setMessage(
                        readPayloadValue(payload, "ticketNo")
                                + " assigned to "
                                + readPayloadValue(payload, "assigneeName"));
            }

            case STATUS_CHANGED -> {
                String newStatus = readPayloadValue(payload, "newStatus");

                if ("RESOLVED".equals(newStatus) || "CLOSED".equals(newStatus)) {
                    dto.setTitle("Resolved");
                    dto.setColor("success");
                } else {
                    dto.setTitle("Status changed");
                    dto.setColor("warning");
                }

                dto.setMessage(
                        readPayloadValue(payload, "ticketNo")
                                + " changed from "
                                + readPayloadValue(payload, "oldStatus")
                                + " to "
                                + newStatus);
            }

            case COMMENT_ADDED -> {
                dto.setTitle("Comment");
                dto.setColor("purple");

                String userName = log.getActor() != null
                        ? log.getActor().getFullName()
                        : "Someone";

                dto.setMessage(
                        userName + " added a note to "
                                + readPayloadValue(payload, "ticketNo"));
            }

            case ATTACHMENT_UPLOADED -> {
                dto.setTitle("Attachment");
                dto.setColor("primary");
                dto.setMessage(
                        readPayloadValue(payload, "fileName")
                                + " uploaded to "
                                + readPayloadValue(payload, "ticketNo"));
            }

            default -> {
                dto.setTitle(log.getAction().name());
                dto.setColor("primary");
                dto.setMessage(log.getEntityType() + " action performed");
            }
        }

        return dto;
    }

    private String readPayloadValue(String payload, String key) {

        if (payload == null || payload.isBlank()) {
            return "";
        }

        try {
            JsonNode node = objectMapper.readTree(payload);

            if (node.has(key) && !node.get(key).isNull()) {
                return node.get(key).asText();
            }

            return "";

        } catch (Exception e) {
            return "";
        }
    }

}
