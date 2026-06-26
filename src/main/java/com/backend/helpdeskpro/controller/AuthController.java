package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.auth.AuthResponseDto;
import com.backend.helpdeskpro.dto.auth.UserLoginDto;
import com.backend.helpdeskpro.dto.auth.UserRegisterDto;
import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/helpdesk/auth")
public class AuthController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> registerUser(
            @RequestBody UserRegisterDto dto) {
        AuthResponseDto saved = userService.registerUser(dto);
        return ResponseEntity.ok(saved);

    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody UserLoginDto request) {
        return ResponseEntity.ok(userService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> currentUser(@AuthenticationPrincipal CustomUserPrincipal principal) {

        User user = userRepository.findById(principal.getUserId())
                .orElseThrow(() -> new RuntimeException("user not found"));

        return ResponseEntity.ok(UserResponseDto.fromEntity(user));

    }

    @PostMapping("/register/bulk")
    public ResponseEntity<List<AuthResponseDto>> registerUserBulk(
            @RequestBody List<UserRegisterDto> dto) {

        List<AuthResponseDto> resp = dto.stream()
                .map(d -> userService.registerUser(d))
                .toList();
        return ResponseEntity.ok(resp);

    }

    @GetMapping("/fetch-all")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/fetch-all-staff")
    public ResponseEntity<List<UserResponseDto>> getAllStaffUsers(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<UserResponseDto> users = userService.getAllStaff(authUser);
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{userId}/activation/{isActive}")
    public ResponseEntity<UserResponseDto> updateStatus(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long userId,
            @PathVariable boolean isActive,
            HttpServletRequest request) {
        return ResponseEntity.ok(userService.updateStatus(userId, isActive, request, authUser));
    }

    @GetMapping("/fetch-agents")
    public ResponseEntity<List<UserResponseDto>> getAllAgents(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody Long supervisorId) {
        List<UserResponseDto> agents = userService.getAgents(authUser);
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/fetch-staffs/{agentId}")
    public ResponseEntity<List<UserResponseDto>> getAllStaffs(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long agentId) {
        List<UserResponseDto> agents = userService.getStaffUsers(authUser, agentId);
        return ResponseEntity.ok(agents);
    }

}
