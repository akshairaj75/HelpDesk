package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByTicketNo(String ticketNo);

    List<Ticket> findByStatusOrderByCreatedAtDesc(TicketStatus status);

    long countByCreatedAtBetween(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay2);
}
