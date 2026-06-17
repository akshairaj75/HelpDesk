package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.sla.SlaPolicyCreateDto;
import com.backend.helpdeskpro.dto.sla.SlaPolicyResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface SlaPolicyService {

    SlaPolicyResponseDto createSlaPolicy(CustomUserPrincipal authUser, SlaPolicyCreateDto dto);

    List<SlaPolicyResponseDto> getAllSlaPolicies();

}
