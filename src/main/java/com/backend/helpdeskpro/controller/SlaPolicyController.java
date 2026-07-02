package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.sla.SlaPolicyCreateDto;
import com.backend.helpdeskpro.dto.sla.SlaPolicyResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.SlaPolicyService;
import com.backend.helpdeskpro.service.TicketService;

@RestController
@RequestMapping("/api/helpdesk/sla-policy")
public class SlaPolicyController {
    @Autowired
    SlaPolicyService slaPolicyService;

    @Autowired
    TicketService ticketService;

    @PostMapping("/add-sla")
    public ResponseEntity<SlaPolicyResponseDto> createSlaPolicy(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody SlaPolicyCreateDto dto) {
        SlaPolicyResponseDto resp = slaPolicyService.createSlaPolicy(authUser, dto);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-sla")
    public ResponseEntity<List<SlaPolicyResponseDto>> getAllSlaPolicies() {
        List<SlaPolicyResponseDto> policies = slaPolicyService.getAllSlaPolicies();
        return ResponseEntity.ok(policies);
    }

    @PostMapping("/add-sla/bulk")
    public ResponseEntity<List<SlaPolicyResponseDto>> createSlaPolicyBulk(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody List<SlaPolicyCreateDto> dto) {
        List<SlaPolicyResponseDto> resp = dto.stream()
                .map(d -> slaPolicyService.createSlaPolicy(authUser, d))
                .toList();
        return ResponseEntity.ok(resp);
    }

}
