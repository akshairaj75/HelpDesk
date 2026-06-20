package com.backend.helpdeskpro.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.audit.AuditLogResponseDto;
import com.backend.helpdeskpro.entity.AuditLog;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.repository.AuditLogRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditLogRepository auditLogRepository;

    private ObjectMapper objectMapper;

    public AuditServiceImpl(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<AuditLogResponseDto> getAllAuditLogs(CustomUserPrincipal authUser) {
        List<AuditLog> auditLogs = auditLogRepository.findAllByOrderByCreatedAtDesc();
        return auditLogs
                .stream()
                .map(AuditLogResponseDto::fromEntity)
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

}
