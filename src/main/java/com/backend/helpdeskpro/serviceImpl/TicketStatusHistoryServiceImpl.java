package com.backend.helpdeskpro.serviceImpl;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.dto.tickets.ticketStatus.TicketStatusHistoryDto;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.TicketStatusHistory;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.enums.NotificationType;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.repository.TicketStatusHistoryRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.NotificationService;
import com.backend.helpdeskpro.service.TicketStatusHistoryService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class TicketStatusHistoryServiceImpl implements TicketStatusHistoryService {

    @Autowired
    TicketStatusHistoryRepository statusHistoryRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    AuditService auditLogService;

    @Override
    public TicketResponseDto updateTicketStatus(
            CustomUserPrincipal authUser,
            TicketStatusHistoryDto dto,
            HttpServletRequest request) {

        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketStatus oldStatus = ticket.getStatus();
        ticket.setStatus(dto.getNewStatus());

        if (dto.getNewStatus() == TicketStatus.RESOLVED) {
            ticket.setResolvedAt(LocalDateTime.now());
        } else if (dto.getNewStatus() == TicketStatus.CLOSED) {
            ticket.setClosedAt(LocalDateTime.now());
        }

        Ticket savedTicket = ticketRepository.save(ticket);

        auditLogService.logAction(
                "TICKET",
                savedTicket.getId(),
                authUser.getUser(),
                AuditAction.STATUS_CHANGED,
                Map.of(
                        "ticketNo", savedTicket.getTicketNo(),
                        "oldStatus", oldStatus.name(),
                        "newStatus", dto.getNewStatus().name(),
                        "reason", dto.getReason() != null ? dto.getReason() : ""),
                request);

        User receiver = null;

        if (ticket.getAssignee() != null && !ticket.getAssignee().getId().equals(authUser.getUserId())) {
            receiver = ticket.getAssignee();
            notificationService.createNotification(
                    receiver,
                    ticket,
                    NotificationType.TICKET_UPDATED,
                    "New comment added",
                    authUser.getUser().getFullName() + " commented on ticket " + ticket.getTicketNo(),
                    "/tickets/" + ticket.getId());
        }

        TicketStatusHistory history = new TicketStatusHistory();
        history.setTicket(ticket);
        history.setOldStatus(oldStatus);
        history.setNewStatus(dto.getNewStatus());
        history.setChangedBy(authUser.getUser());
        history.setReason(dto.getReason());
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        return TicketResponseDto.fromEntity(ticket);

    }

}
