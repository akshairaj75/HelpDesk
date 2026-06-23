package com.backend.helpdeskpro.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserResponseDto> getAllUsers(CustomUserPrincipal authUser) {
        return userRepository.findAllByOrderByCreatedAtDesc()
        .stream()
        .map(UserResponseDto::fromEntity)
        .toList();
        
    }

}
