package com.backend.helpdeskpro.controller.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.tickets.ticketTag.TicketTagCreateDto;
import com.backend.helpdeskpro.dto.tickets.ticketTag.TicketTagResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketTagService;

@RequestMapping("/api/helpdesk/ticket/tags")
@RestController
public class TicketTagsController {

    @Autowired
    TicketTagService ticketTagService;
    
    @PostMapping("/add-ticket-tag")
    public ResponseEntity<TicketTagResponseDto> createTicketTag(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody TicketTagCreateDto dto) {
        TicketTagResponseDto resp = ticketTagService.createTicketTag(authUser, dto);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-ticket-tag")
    public ResponseEntity<List<TicketTagResponseDto>> getAllTicketTags() {
        List<TicketTagResponseDto> tags = ticketTagService.getAllTicketTags();
        return ResponseEntity.ok(tags);
    }

    @PostMapping("/add-ticket-tag/bulk")
    public ResponseEntity<List<TicketTagResponseDto>> createTicketTagBulk(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody List<TicketTagCreateDto> dto) {
        List<TicketTagResponseDto> resp = dto.stream()
                .map(d -> ticketTagService.createTicketTag(authUser, d))
                .toList();
        return ResponseEntity.ok(resp);
    }
    


}
