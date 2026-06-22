package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.audit.AuditLogResponseDto;
import com.backend.helpdeskpro.dto.audit.RecentActivityDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;

@RestController
@RequestMapping("/api/helpdesk/audit")
public class AuditController {

    @Autowired
    AuditService auditService;

    @GetMapping("/audit-logs")
    public List<RecentActivityDto> getAllAuditLogs(CustomUserPrincipal authUser) {
        return auditService.getAllAuditLogs(authUser);
    }

}
