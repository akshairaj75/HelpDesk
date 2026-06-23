package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.auth.AuthResponseDto;
import com.backend.helpdeskpro.dto.auth.UserLoginDto;
import com.backend.helpdeskpro.dto.auth.UserRegisterDto;
import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    AuthResponseDto registerUser(UserRegisterDto dto);

    List<UserResponseDto> getUsers();
        List<UserResponseDto> getStaffUsers(CustomUserPrincipal authUser);

    AuthResponseDto login(UserLoginDto dto);

    UserResponseDto updateStatus(Long userId, boolean isActive, HttpServletRequest request, CustomUserPrincipal authUser);

}
