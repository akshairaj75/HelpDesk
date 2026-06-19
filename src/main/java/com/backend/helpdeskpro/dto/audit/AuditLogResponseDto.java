package com.backend.helpdeskpro.dto.audit;

import com.backend.helpdeskpro.entity.AuditLog;

public class AuditLogResponseDto {
    private Long id;
    private String entityType;
    private Long entityId;
    private String performedBy;
    private String action;
    private String ipAdress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
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
        dto.setId(auditLog.getId());
        dto.setEntityType(auditLog.getEntityType());
        dto.setEntityId(auditLog.getEntityId());
        // dto.setPerformedBy(auditLog.getPerformedBy());
        dto.setAction(auditLog.getAction());
        // dto.setIpAdress(auditLog.getIpAdress());
        return dto;
    }

}
