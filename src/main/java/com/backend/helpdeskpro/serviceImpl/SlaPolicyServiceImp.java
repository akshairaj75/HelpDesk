package com.backend.helpdeskpro.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.sla.SlaPolicyCreateDto;
import com.backend.helpdeskpro.dto.sla.SlaPolicyResponseDto;
import com.backend.helpdeskpro.entity.SlaPolicy;
import com.backend.helpdeskpro.repository.SlaPolicyRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.SlaPolicyService;

import jakarta.transaction.Transactional;

@Service
public class SlaPolicyServiceImp implements SlaPolicyService{

    @Autowired
    SlaPolicyRepository slaPolicyRepository;

    @Transactional
    @Override
    public SlaPolicyResponseDto createSlaPolicy(CustomUserPrincipal authUser, SlaPolicyCreateDto dto) {
        SlaPolicy slaPolicy = new SlaPolicy();
        slaPolicy.setName(dto.getName());
        slaPolicy.setPriorityLevel(dto.getPriorityLevel());
        slaPolicy.setEscalateEnabled(dto.isEscalateEnabled());
        slaPolicy.setEscalationMinutes(dto.getEscalationMinutes());
        slaPolicy.setResponseMinutes(dto.getResponseMinutes());
        slaPolicy.setResolutionMinutes(dto.getResolutionMinutes());
        slaPolicy.setActive(dto.isActive());

        slaPolicyRepository.save(slaPolicy);

        return SlaPolicyResponseDto.fromEntity(slaPolicy);
     }

    @Override
    public List<SlaPolicyResponseDto> getAllSlaPolicies() {
        List<SlaPolicy> policies = slaPolicyRepository.findAll();
        return policies.stream()
                .map(SlaPolicyResponseDto::fromEntity)
                .toList();
                
       }

}
