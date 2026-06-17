package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.TicketTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TicketTagRepository extends JpaRepository<TicketTag, Integer> {

    Optional<TicketTag> findByNameIgnoreCase(String name);
}
