package com.backend.helpdeskpro.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.department.DepartmentCreateDto;
import com.backend.helpdeskpro.dto.department.DepartmentResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.DepartmentService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/helpdesk/department")
public class DepartmentController {
    
    @Autowired
    DepartmentService departmentService;

    @PostMapping("/add-department")
    public ResponseEntity<DepartmentResponseDto> createDepartment(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @RequestBody DepartmentCreateDto dto,
        HttpServletRequest request
    ){
        DepartmentResponseDto resp = departmentService.createDepartment(authUser, dto, request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/get-all-department")
    public ResponseEntity<List<DepartmentResponseDto>> getAllDepartments() {
        List<DepartmentResponseDto> departments = departmentService.getAllDepartments();
        return ResponseEntity.ok(departments);
    }

    @GetMapping("/get-department/{departmentId}")
    public ResponseEntity<DepartmentResponseDto> getDepartmentById(@PathVariable Integer departmentId) {
        DepartmentResponseDto department = departmentService.getDepartmentById(departmentId);
        return ResponseEntity.ok(department);
    }

   
    @PostMapping("/add-department/bulk")
    public ResponseEntity<List<DepartmentResponseDto>> createDepartmentBulk(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @RequestBody List<DepartmentCreateDto> dto
    ){
        List<DepartmentResponseDto> resp = departmentService.createDepartmentBulk(authUser, dto);
        return ResponseEntity.ok(resp);
    } 
    
    
    
}
