package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.backend.helpdeskpro.dto.auth.UserRegisterDto;
import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.dashboard.AgentWorkloadDto;
import com.backend.helpdeskpro.dto.department.DepartmentResponseDto;
import com.backend.helpdeskpro.enums.UserRole;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AdminService;
import com.backend.helpdeskpro.service.DepartmentService;
import com.backend.helpdeskpro.service.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/helpdesk/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/fetch/users")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEAM_LEAD')")
    public ResponseEntity<List<UserResponseDto>> getAllUsers(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<UserResponseDto> users = adminService.getAllUsers(authUser);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/agent-workload")
    public ResponseEntity<List<AgentWorkloadDto>> getAgentWorkload(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<AgentWorkloadDto> agents = adminService.getAgentWorkload(authUser);
        return ResponseEntity.ok(agents);
    }

    @GetMapping("/{departmentId}/get-agents")
    public ResponseEntity<List<UserResponseDto>> getAgentsByDepartment(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Integer departmentId,
            HttpServletRequest request) {
        if (authUser.getUser().getRole() == UserRole.ADMIN || authUser.getUser().getRole() == UserRole.TEAM_LEAD) {
            List<UserResponseDto> agents = departmentService.getAgentsByDepartment(authUser, departmentId);
            return ResponseEntity.ok(agents);
        }

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not allowed to perform this action");
    }

    @PatchMapping("assign-department-manager/{departmentId}")
    public ResponseEntity<DepartmentResponseDto> assignDepartmentManager(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Integer departmentId,
            @RequestBody Long managerId,
            HttpServletRequest request) {

        if (authUser.getUser().getRole() == UserRole.ADMIN || authUser.getUser().getRole() == UserRole.TEAM_LEAD) {
            DepartmentResponseDto resp = departmentService.assignDepartmentManager(authUser, departmentId, managerId,
                    request);
            return ResponseEntity.ok(resp);
        }

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not allowed to perform this action");
    }

    @PatchMapping("/assign-agent")
    public ResponseEntity<UserResponseDto> assignAgent(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody Long agentId,
            @RequestBody Long staffId,
            HttpServletRequest request) {
        if (authUser.getUser().getRole() == UserRole.ADMIN || authUser.getUser().getRole() == UserRole.TEAM_LEAD) {
            UserResponseDto resp = userService.assignStaff(authUser, agentId, staffId, request);
            return ResponseEntity.ok(resp);
        }

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not allowed to perform this action");
    }

    @GetMapping("/fetch-agents")
    public ResponseEntity<List<UserResponseDto>> fetchAgents(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<UserResponseDto> agents = adminService.getAllAgents(authUser);
        return ResponseEntity.ok(agents);
    }

        @GetMapping("/fetch-all-staff-by-agent/{agentId}")
    public ResponseEntity<List<UserResponseDto>> getStaffsByAgent(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long agentId) {
        List<UserResponseDto> agents = adminService.getStaffsByAgent(authUser, agentId);
        return ResponseEntity.ok(agents);
    }

    @PostMapping("/create-subordinate")
    public ResponseEntity<UserResponseDto> createSubordinate(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody UserRegisterDto dto,
            HttpServletRequest request) {
        if (authUser.getUser().getRole() == UserRole.ADMIN || authUser.getUser().getRole() == UserRole.TEAM_LEAD ||
                authUser.getUser().getRole() == UserRole.AGENT) {
            UserResponseDto resp = userService.createSubordinate(authUser, dto, request);
            return ResponseEntity.ok(resp);
        }

        throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                "You are not allowed to perform this action");
    }

}
