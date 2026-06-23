package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AdminService;

@RestController
@RequestMapping("/api/helpdesk/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @GetMapping("/fetch/users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
        @AuthenticationPrincipal CustomUserPrincipal authUser
    ) {
        List<UserResponseDto> users = adminService.getAllUsers(authUser);
        return ResponseEntity.ok(users);
    }
}
