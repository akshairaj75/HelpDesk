package com.backend.helpdeskpro.controller.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketStatusHistoryService;

@RequestMapping("/api/helpdesk/ticket/status")
@RestController
public class TicketStatusHistoryController {

    @Autowired
    TicketStatusHistoryService ticketStatusService;


    @PatchMapping("/{ticketId}")
    public ResponseEntity<TicketResponseDto> updateTicketStatus(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId,
            @RequestParam TicketStatus status,
            @RequestParam(required = false) String reason) {
        TicketResponseDto updatedTicket = ticketStatusService.updateTicketStatus(authUser, ticketId, status, reason);
        return ResponseEntity.ok(updatedTicket);
    }

}
