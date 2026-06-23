package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.SlaPolicy;
import com.backend.helpdeskpro.enums.PriorityLevel;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SlaPolicyRepository extends JpaRepository<SlaPolicy, Integer> {

    Optional<SlaPolicy> findByPriorityLevel(PriorityLevel priorityLevel);

}
