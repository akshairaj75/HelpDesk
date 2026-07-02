package com.backend.helpdeskpro.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.auth.UserRegisterDto;
import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.TicketStatus;
import com.backend.helpdeskpro.enums.UserRole;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers(CustomUserPrincipal authUser) {
        return userRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(UserResponseDto::fromEntity)
                .toList();

    }

    // @Override
    // public List<AgentWorkloadDto> getAgentWorkload(CustomUserPrincipal authUser)
    // {

    // List<UserRole> agentRoles = List.of(
    // UserRole.STAFF,
    // UserRole.AGENT,
    // UserRole.TEAM_LEAD);

    // List<TicketStatus> activeStatuses = List.of(
    // TicketStatus.OPEN,
    // TicketStatus.IN_PROGRESS,
    // TicketStatus.ON_HOLD);

    // List<AgentWorkloadDto> workloads =
    // userRepository.getAgentWorkload(agentRoles, activeStatuses);

    // int maxTicketLimit = 8; // assume 8 active tickets = 100% workload

    // for (AgentWorkloadDto dto : workloads) {
    // int percentage = (int) Math.min(
    // 100,
    // Math.round((dto.getActiveTicketCount() * 100.0) / maxTicketLimit));

    // dto.setWorkloadPercentage(percentage);
    // }

    // return workloads;
    // }
    @Override
    public List<AgentWorkloadDto> getAgentWorkload(CustomUserPrincipal authUser) {

        List<TicketStatus> activeStatuses = List.of(
                TicketStatus.OPEN,
                TicketStatus.IN_PROGRESS,
                TicketStatus.ON_HOLD);

        List<AgentWorkloadDto> workloads;
        if (authUser.getUser().getRole() == UserRole.AGENT) {

            // workloads = userRepository.getStaffWorkloadByAgent(
            // authUser.getUser().getId(),
            // activeStatuses);

            workloads = userRepository.getStaffWorkloadByAgent(
                    authUser.getUser().getId(),
                    UserRole.STAFF,
                    activeStatuses);

        } else if (authUser.getUser().getRole() == UserRole.STAFF
                || authUser.getUser().getRole() == UserRole.END_USER) {

            throw new RuntimeException("You are not allowed to perform this action");

        } else {
            // workloads = userRepository.getAgentWorkload(

            // List.of(UserRole.STAFF, UserRole.AGENT, UserRole.TEAM_LEAD),
            // activeStatuses);

            workloads = userRepository.getAgentWorkloadWithStaffs(
                    UserRole.AGENT,
                    activeStatuses);
        }

        int maxTicketLimit = 8; // assume 8 active tickets = 100% workload

        for (AgentWorkloadDto dto : workloads) {
            int percentage = (int) Math.min(
                    100,
                    Math.round((dto.getActiveTicketCount() * 100.0) / maxTicketLimit));

            dto.setWorkloadPercentage(percentage);
        }

        return workloads;
    }

    @Override
    public List<UserResponseDto> getAllAgents(CustomUserPrincipal authUser) {
        List<UserResponseDto> agents = userRepository.findByRole(UserRole.AGENT)
                .stream()
                .map(UserResponseDto::fromEntity)
                .toList();

        return agents;
    }

    @Override
    public List<UserResponseDto> getStaffsByAgent(CustomUserPrincipal authUser, Long agentId) {

        if (authUser.getUser().getRole() == UserRole.AGENT) {
            List<User> users = userRepository.findByRoleAndSupervisor_Id(UserRole.STAFF, authUser.getUserId());
            return users.stream()
                    .map(UserRegisterDto::fromEntity)
                    .toList();

        } else if (authUser.getUser().getRole() == UserRole.TEAM_LEAD
                || authUser.getUser().getRole() == UserRole.ADMIN) {

            List<User> users = userRepository.findByRoleAndSupervisor_Id(UserRole.STAFF, agentId);

            return users.stream()
                    .map(UserRegisterDto::fromEntity)
                    .toList();
        } else {
            throw new RuntimeException("You are not allowed to perform this action");
        }
    }

}
