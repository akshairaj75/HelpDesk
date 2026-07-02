package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.Department;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByTicketNo(String ticketNo);

    List<Ticket> findByStatusOrderByCreatedAtDesc(TicketStatus status);

    long countByCreatedAtBetween(LocalDateTime atStartOfDay, LocalDateTime atStartOfDay2);

    List<Ticket> findByReporter(User user);

    List<Ticket> findByAssignee(User user);

    List<Ticket> findBySlaDueAtBeforeAndSlaBreachedFalseAndStatusNotIn(
            LocalDateTime now,
            List<TicketStatus> statuses);

    List<Ticket> findByDepartment(Department department);

    List<Ticket> findByAssigneeOrAssigneeSupervisor(User assignee, User supervisor);

    List<Ticket> findBySlaBreachedTrueAndStatusNotIn(List<TicketStatus> excluded);

    List<Ticket> findBySlaBreachedTrueAndStatusNotInAndAssigneeId(
            List<TicketStatus> statuses,
            Long assigneeId);

    List<Ticket> findBySlaBreachedTrueAndStatusNotInAndAssigneeIdOrSlaBreachedTrueAndStatusNotInAndAssigneeSupervisorId(
            List<TicketStatus> statuses1,
            Long assigneeId,
            List<TicketStatus> statuses2,
            Long supervisorId);

}
