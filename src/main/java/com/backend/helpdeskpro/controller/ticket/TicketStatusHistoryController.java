package com.backend.helpdeskpro.controller.ticket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.dto.tickets.ticketStatus.TicketStatusHistoryDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketStatusHistoryService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/helpdesk/tickets/status")
@RestController
public class TicketStatusHistoryController {

    @Autowired
    TicketStatusHistoryService ticketStatusService;

    @PatchMapping("/{ticketId}/update")
    public ResponseEntity<TicketResponseDto> updateTicketStatus(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId,
            @RequestBody TicketStatusHistoryDto dto,
            HttpServletRequest request) {
        dto.setTicketId(ticketId);

        System.out.println("||||||||||||||||||||||||");
        System.out.println(dto.getNewStatus());
        System.out.println("||||||||||||||||||||||||");
        TicketResponseDto updatedTicket = ticketStatusService.updateTicketStatus(authUser, dto, request);
        return ResponseEntity.ok(updatedTicket);
    }
}
