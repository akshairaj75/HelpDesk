package com.backend.helpdeskpro.service;

import java.util.List;
import java.util.Map;

import com.backend.helpdeskpro.dto.audit.AuditLogResponseDto;
import com.backend.helpdeskpro.dto.audit.RecentActivityDto;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface AuditService {

    void logAction(
            String entityType,
            Long entityId,
            User performedBy,
            AuditAction action,
            Map<String, Object> payload,
            HttpServletRequest request);

    List<AuditLogResponseDto> getLogsByEntity(String entityType, Long entityId);

    List<AuditLogResponseDto> getLogsByUser(Long userId);

    // List<AuditLogResponseDto> getAllAuditLogs(CustomUserPrincipal authUser);
    List<RecentActivityDto> getAllAuditLogs(CustomUserPrincipal authUser);

}
