package com.backend.helpdeskpro.serviceImpl;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.dto.tickets.ticketStatus.TicketStatusHistoryDto;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.TicketStatusHistory;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.repository.TicketStatusHistoryRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.TicketStatusHistoryService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TicketStatusHistoryServiceImpl implements TicketStatusHistoryService {

    @Autowired
    TicketStatusHistoryRepository statusHistoryRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    AuditService auditLogService;

    @Override
    public TicketResponseDto updateTicketStatus(
            CustomUserPrincipal authUser,
            TicketStatusHistoryDto dto,
            HttpServletRequest request) {
        System.out.println("--===========================================");

        System.out.println("DTO newStatus = " + dto.getNewStatus());
        System.out.println("DTO ticketId = " + dto.getTicketId());

        System.out.println("--===========================================");

        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketStatus oldStatus = ticket.getStatus();

        ticket.setStatus(dto.getNewStatus());

        if (dto.getNewStatus() == TicketStatus.RESOLVED) {
            ticket.setResolvedAt(LocalDateTime.now());
        } else if (dto.getNewStatus() == TicketStatus.CLOSED) {
            ticket.setClosedAt(LocalDateTime.now());
            ticket.setResolvedAt(LocalDateTime.now());
        } else {
            ticket.setResolvedAt(null);
            ticket.setClosedAt(null);
        }

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketStatusHistory history = new TicketStatusHistory();
        history.setTicket(savedTicket);
        history.setOldStatus(oldStatus);
        history.setNewStatus(savedTicket.getStatus());
        history.setChangedBy(authUser.getUser());
        history.setReason(dto.getReason());
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        auditLogService.logAction(
                "TICKET",
                savedTicket.getId(),
                authUser.getUser(),
                AuditAction.STATUS_CHANGED,
                Map.of(
                        "ticketNo", savedTicket.getTicketNo(),
                        "oldStatus", oldStatus.name(),
                        "newStatus", savedTicket.getStatus().name(),
                        "reason", dto.getReason() != null ? dto.getReason() : ""),
                request);

        return TicketResponseDto.fromEntity(ticket);

    }

}
