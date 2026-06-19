package com.backend.helpdeskpro.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.audit.AuditLogResponseDto;
import com.backend.helpdeskpro.entity.AuditLog;
import com.backend.helpdeskpro.repository.AuditLogRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    AuditLogRepository auditLogRepository;

    // @Override
    // public AuditLogResponseDto getAuditLogs(CustomUserPrincipal authUser, Long ticketId) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getAuditLogs'");
    // }

    // @Override
    // public void logAction(String action, String entityType, Long entityId, String entityName, String actorName,String actorEmail) {
    //     AuditLog log = new AuditLog();
    //     log.setAction(action);
    //     log.setEntityType(entityType);
    //     log.setEntityId(entityId);
    //     log.setEntityName(entityName);
    //     log.setActorName(actorName);
    //     log.setActorEmail(actorEmail);
    //     auditLogRepository.save(log);
    // }

}
