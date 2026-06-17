package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}
