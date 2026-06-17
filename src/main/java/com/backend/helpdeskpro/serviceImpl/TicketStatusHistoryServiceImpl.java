package com.backend.helpdeskpro.serviceImpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.tickets.ticketDto.TicketResponseDto;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.TicketStatusHistory;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.repository.TicketStatusHistoryRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketStatusHistoryService;

@Service
public class TicketStatusHistoryServiceImpl implements TicketStatusHistoryService {

    @Autowired
    TicketStatusHistoryRepository statusHistoryRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Override
    public TicketResponseDto updateTicketStatus(
            CustomUserPrincipal authUser,
            Long ticketId,
            TicketStatus status,
            String reason) {

        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketStatus oldStatus = ticket.getStatus();
        ticket.setStatus(status);

        if (status == TicketStatus.RESOLVED) {
            ticket.setResolvedAt(LocalDateTime.now());
        } else if (status == TicketStatus.CLOSED) {
            ticket.setClosedAt(LocalDateTime.now());
        }

        ticketRepository.save(ticket);

        TicketStatusHistory history = new TicketStatusHistory();
        history.setTicket(ticket);
        history.setOldStatus(oldStatus);
        history.setNewStatus(status);
        history.setChangedBy(authUser.getUser());
        history.setReason(reason);
        history.setChangedAt(LocalDateTime.now());

        statusHistoryRepository.save(history);

        return TicketResponseDto.fromEntity(ticket);

    }

}
