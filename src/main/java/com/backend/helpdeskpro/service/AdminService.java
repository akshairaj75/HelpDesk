package com.backend.helpdeskpro.service;

import java.util.List;


import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface AdminService {

    List<UserResponseDto> getAllUsers(CustomUserPrincipal authUser);


    List<AgentWorkloadDto> getAgentWorkload(CustomUserPrincipal authUser);


    List<UserResponseDto> getAllAgents(CustomUserPrincipal authUser);


}
