package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.enums.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);

    List<User> findByRole(UserRole role);

    List<User> findAllByOrderByCreatedAtDesc();

    @Query("""
            SELECT new com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto(
                u.id,
                u.fullName,
                u.email,
                COUNT(t)
            )
            FROM User u
            LEFT JOIN Ticket t
                ON t.assignee = u
                AND t.status IN :activeStatuses
            WHERE u.role IN :agentRoles

            GROUP BY u.id, u.fullName, u.email
            ORDER BY COUNT(t) DESC
            """)
    List<AgentWorkloadDto> getAgentWorkload(
            @Param("agentRoles") List<UserRole> agentRoles,
            @Param("activeStatuses") List<TicketStatus> activeStatuses);

    @Query("""
            SELECT new com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto(
                agent.id,
                agent.fullName,
                agent.email,
                COUNT(t)
            )
            FROM User agent
            LEFT JOIN User staff
                ON staff.supervisor = agent
            LEFT JOIN Ticket t
                ON t.assignee = agent
                OR t.assignee = staff
            WHERE agent.role = :agentRole
              AND t.status IN :activeStatuses
            GROUP BY agent.id, agent.fullName, agent.email
            ORDER BY COUNT(t) DESC
            """)
    List<AgentWorkloadDto> getAgentWorkloadWithStaffs(
            @Param("agentRole") UserRole agentRole,
            @Param("activeStatuses") List<TicketStatus> activeStatuses);

    @Query("""
    SELECT new com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto(
    u.id,
    u.fullName,
    u.email,
    COUNT(t)
    )
    FROM User u
    LEFT JOIN Ticket t
    ON t.assignee = u
    AND t.status IN :activeStatuses
    WHERE u.role = com.backend.helpdeskpro.enums.UserRole.STAFF
    AND u.active = true
    AND u.supervisor.id = :agentId
    GROUP BY u.id, u.fullName, u.email
    ORDER BY COUNT(t) DESC
    """)
    List<AgentWorkloadDto> getStaffWorkloadByAgent(
    @Param("agentId") Long agentId,
    @Param("activeStatuses") List<TicketStatus> activeStatuses);

    @Query("""
            SELECT new com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto(
                staff.id,
                staff.fullName,
                staff.email,
                COUNT(t)
            )
            FROM User staff
            LEFT JOIN Ticket t
                ON t.assignee = staff
                AND t.status IN :activeStatuses
            WHERE staff.supervisor.id = :agentId
              AND staff.role = :staffRole
            GROUP BY staff.id, staff.fullName, staff.email
            ORDER BY COUNT(t) DESC
            """)
    List<AgentWorkloadDto> getStaffWorkloadByAgent(
            @Param("agentId") Long agentId,
            @Param("staffRole") UserRole staffRole,
            @Param("activeStatuses") List<TicketStatus> activeStatuses);

    List<User> findUsersByDepartment_Id(Integer departmentId);

    // List<User> findByRoleAndSupervisor(UserRole staff, User user);
    List<User> findByRoleAndSupervisor_Id(UserRole staff, Long supervisorId);

    List<User> findBySupervisor_IdAndActiveTrue(Long userId);

    List<User> findBySupervisor_Id(Long userId);
}
