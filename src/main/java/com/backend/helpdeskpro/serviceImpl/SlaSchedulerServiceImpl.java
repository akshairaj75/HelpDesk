package com.backend.helpdeskpro.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.enums.NotificationType;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.NotificationService;
import com.backend.helpdeskpro.service.SlaSchedulerService;

@Service
public class SlaSchedulerServiceImpl implements SlaSchedulerService {

    private final TicketRepository ticketRepository;
    private final NotificationService notificationService;
    private final AuditService auditLogService;

    public SlaSchedulerServiceImpl(
            TicketRepository ticketRepository,
            NotificationService notificationService,
            AuditService auditLogService) {

        this.ticketRepository = ticketRepository;
        this.notificationService = notificationService;
        this.auditLogService = auditLogService;
    }

    @Scheduled(fixedRate = 300000) // every 5 minutes
    @Transactional
    @Override
    public void checkSlaBreaches() {

        List<TicketStatus> excludedStatuses = List.of(
                TicketStatus.RESOLVED,
                TicketStatus.CLOSED,
                TicketStatus.CANCELLED
        );

        List<Ticket> breachedTickets =
                ticketRepository.findBySlaDueAtBeforeAndSlaBreachedFalseAndStatusNotIn(
                        LocalDateTime.now(),
                        excludedStatuses
                );

        for (Ticket ticket : breachedTickets) {

            ticket.setSlaBreached(true);
            ticket.setSlaBreachNotified(true);

            Ticket savedTicket = ticketRepository.save(ticket);

            if (savedTicket.getAssignee() != null) {
                notificationService.createNotification(
                        savedTicket.getAssignee(),
                        savedTicket,
                        NotificationType.SLA_BREACHED,
                        "SLA breached",
                        "Ticket " + savedTicket.getTicketNo() + " has breached SLA.",
                        "/tickets/" + savedTicket.getId()
                );
            }

            auditLogService.logAction(
                    "TICKET",
                    savedTicket.getId(),
                    null,
                    AuditAction.SLA_BREACHED,
                    Map.of(
                            "ticketNo", savedTicket.getTicketNo(),
                            "subject", savedTicket.getSubject(),
                            "priority", savedTicket.getPriority().name(),
                            "slaDueAt", savedTicket.getSlaDueAt().toString()
                    ),
                    null
            );
        }
    }
}