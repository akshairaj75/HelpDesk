package com.backend.helpdeskpro.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.service.AuditService;

@RestController
@RequestMapping("/api/helpdesk/audit")
public class AuditController {

    @Autowired
    AuditService auditService;

    // @GetMapping("/logs")
    // public 

    

}
