package com.backend.helpdeskpro.service;

import java.util.List;


import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface AdminService {

    List<UserResponseDto> getAllUsers(CustomUserPrincipal authUser);


}
