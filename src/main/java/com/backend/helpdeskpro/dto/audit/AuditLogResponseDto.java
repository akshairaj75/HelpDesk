package com.backend.helpdeskpro.dto.audit;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.entity.AuditLog;
import com.backend.helpdeskpro.enums.AuditAction;

public class AuditLogResponseDto {
    private Long auditLogId;
    private String entityType;
    private Long entityId;
    private UserResponseDto performedBy;
    private String payload;
    private AuditAction action;
    private String ipAdress;

    public Long getAuditLogId() {
        return auditLogId;
    }

    public void setAuditLogId(Long auditLogId) {
        this.auditLogId = auditLogId;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }

    public UserResponseDto getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(UserResponseDto performedBy) {
        this.performedBy = performedBy;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public AuditAction getAction() {
        return action;
    }

    public void setAction(AuditAction action) {
        this.action = action;
    }

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public static AuditLogResponseDto fromEntity(AuditLog auditLog) {
        AuditLogResponseDto dto = new AuditLogResponseDto();
        dto.setAuditLogId(auditLog.getId());
        dto.setEntityType(auditLog.getEntityType());
        dto.setEntityId(auditLog.getEntityId());
        if (auditLog.getActor() != null) {
            dto.setPerformedBy(UserResponseDto.fromEntity(auditLog.getActor()));
        }
        dto.setPayload(auditLog.getPayload());
        dto.setAction(auditLog.getAction());
        dto.setIpAdress(auditLog.getIpAddress());
        return dto;
    }

}
