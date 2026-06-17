package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.TicketTagMap;
import com.backend.helpdeskpro.entity.TicketTagMapId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketTagMapRepository extends JpaRepository<TicketTagMap, TicketTagMapId> {

    List<TicketTagMap> findByTicketId(Long ticketId);
}
