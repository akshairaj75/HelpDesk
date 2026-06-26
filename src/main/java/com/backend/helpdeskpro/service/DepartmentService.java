package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.department.DepartmentCreateDto;
import com.backend.helpdeskpro.dto.department.DepartmentResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(CustomUserPrincipal authUser, DepartmentCreateDto dto, HttpServletRequest request);

    List<DepartmentResponseDto> getAllDepartments();

    DepartmentResponseDto getDepartmentById(Integer departmentId);

    List<DepartmentResponseDto> createDepartmentBulk(CustomUserPrincipal authUser, List<DepartmentCreateDto> dto);

    DepartmentResponseDto assignDepartmentManager(CustomUserPrincipal authUser, Integer departmentId, Long managerId, HttpServletRequest request);

    List<UserResponseDto> getAgentsByDepartment(CustomUserPrincipal authUser, Integer departmentId);

}
