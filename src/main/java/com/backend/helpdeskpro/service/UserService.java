package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.auth.AuthResponseDto;
import com.backend.helpdeskpro.dto.auth.UserLoginDto;
import com.backend.helpdeskpro.dto.auth.UserRegisterDto;
import com.backend.helpdeskpro.dto.auth.UserResponseDto;

public interface UserService {

    AuthResponseDto registerUser(UserRegisterDto dto);

    List<UserResponseDto> getUsers();

    AuthResponseDto login(UserLoginDto dto);

}
