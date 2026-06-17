package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.department.DepartmentCreateDto;
import com.backend.helpdeskpro.dto.department.DepartmentResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface DepartmentService {

    DepartmentResponseDto createDepartment(CustomUserPrincipal authUser, DepartmentCreateDto dto);

    List<DepartmentResponseDto> getAllDepartments();

    DepartmentResponseDto getDepartmentById(Integer departmentId);

    List<DepartmentResponseDto> createDepartmentBulk(CustomUserPrincipal authUser, List<DepartmentCreateDto> dto);

}
