package com.backend.helpdeskpro.controller.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketAttachment.TicketAttachmentDto;
import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketCreateDto;
import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketService;
import com.backend.helpdeskpro.service.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/helpdesk/tickets")
@RestController
public class TicketController {

    @Autowired
    TicketService ticketService;

    @Autowired
    UserService userService;

    @PostMapping("/create")
    public ResponseEntity<TicketResponseDto> createTicket(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestPart("data") TicketCreateDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            HttpServletRequest request) {

        TicketResponseDto res = ticketService.createTicket(authUser, dto, files, request);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/get-all-ticket")
    public ResponseEntity<List<TicketResponseDto>> getAllTickets(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<TicketResponseDto> tickets = ticketService.getAllTickets(authUser);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/get-ticket/{ticketId}")
    public ResponseEntity<TicketResponseDto> getTicketById(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId) {
        TicketResponseDto ticket = ticketService.getTicketById(authUser, ticketId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping("/get-by-reporter/{userId}")
    public ResponseEntity<List<TicketResponseDto>> getTicketsByReporterId(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long userId) {
        List<TicketResponseDto> tickets = ticketService.getTicketsByReporterId(authUser, userId);
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/{ticketId}/get-attachments")
    public ResponseEntity<List<TicketAttachmentDto>> getAttachmentsByTicketId(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId) {
        TicketResponseDto ticket = ticketService.getTicketById(authUser, ticketId);
        return ResponseEntity.ok(ticket.getAttachments());
    }

    @PostMapping("/{ticketId}/add-attachment")
    public ResponseEntity<TicketResponseDto> addAttachment(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId,
            @RequestPart("files") List<MultipartFile> files,
            HttpServletRequest request) {

        ticketService.addAttachment(authUser, ticketId, files, request);

        return ResponseEntity.ok(ticketService.getTicketById(authUser, ticketId));
    }

    @PutMapping("/{ticketId}/assign/{assigneeId}")
    public ResponseEntity<TicketResponseDto> assignTicket(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId,
            @PathVariable Long assigneeId,
            HttpServletRequest request) {
        TicketResponseDto ticket = ticketService.assignTicket(authUser, ticketId, assigneeId, request);
        return ResponseEntity.ok(ticket);
    }

}
