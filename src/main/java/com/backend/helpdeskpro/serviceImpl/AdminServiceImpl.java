package com.backend.helpdeskpro.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto;
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

    @Override
    public List<AgentWorkloadDto> getAgentWorkload(CustomUserPrincipal authUser) {

        List<UserRole> agentRoles = List.of(
                UserRole.STAFF,
                UserRole.AGENT,
                UserRole.TEAM_LEAD);

        List<TicketStatus> activeStatuses = List.of(
                TicketStatus.OPEN,
                TicketStatus.IN_PROGRESS,
                TicketStatus.ON_HOLD);

        List<AgentWorkloadDto> workloads = userRepository.getAgentWorkload(agentRoles, activeStatuses);

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

}
