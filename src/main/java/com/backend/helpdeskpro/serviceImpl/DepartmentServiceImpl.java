package com.backend.helpdeskpro.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.department.DepartmentCreateDto;
import com.backend.helpdeskpro.dto.department.DepartmentResponseDto;
import com.backend.helpdeskpro.entity.Department;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.enums.UserRole;
import com.backend.helpdeskpro.repository.DepartmentRepository;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.DepartmentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

        @Autowired
        DepartmentRepository departmentRepository;

        @Autowired
        UserRepository userRepository;

        @Autowired
        AuditService auditLogService;

        @Transactional
        @Override
        public DepartmentResponseDto createDepartment(CustomUserPrincipal authUser, DepartmentCreateDto dto,
                        HttpServletRequest request) {

                User manager = userRepository.findById(authUser.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                Department department = new Department();
                department.setName(dto.getName());
                department.setDescription(dto.getDescription());
                department.setActive(dto.getIsActive());
                department.setManager(manager);

                Department savedDepartment = departmentRepository.save(department);

                auditLogService.logAction(
                                "DEPARTMENT",
                                Long.valueOf(savedDepartment.getDepartmentId()),
                                authUser.getUser(),
                                AuditAction.CREATED,
                                Map.of(
                                                "name", savedDepartment.getName()),
                                request);

                return DepartmentResponseDto.fromEntity(savedDepartment);

        }

        @Override
        public List<DepartmentResponseDto> getAllDepartments() {
                List<Department> departments = departmentRepository.findAll();
                return departments.stream()
                                .map(DepartmentResponseDto::fromEntity)
                                .toList();

        }

        @Override
        public DepartmentResponseDto getDepartmentById(Integer departmentId) {
                Department department = departmentRepository.findById(departmentId)
                                .orElseThrow(() -> new RuntimeException("Department not found"));
                return DepartmentResponseDto.fromEntity(department);

        }

        @Transactional
        @Override
        public List<DepartmentResponseDto> createDepartmentBulk(CustomUserPrincipal authUser,
                        List<DepartmentCreateDto> dto) {

                User manager = userRepository.findById(authUser.getUserId())
                                .orElseThrow(() -> new RuntimeException("User not found"));

                List<Department> departments = dto.stream().map(d -> {
                        Department department = new Department();
                        department.setName(d.getName());
                        department.setDescription(d.getDescription());
                        department.setActive(d.getIsActive());
                        department.setManager(manager);
                        return department;
                }).toList();

                List<Department> savedDepartments = departmentRepository.saveAll(departments);

                return savedDepartments.stream()
                                .map(DepartmentResponseDto::fromEntity)
                                .toList();

        }

        @Transactional
        @Override
        public DepartmentResponseDto assignDepartmentManager(
                        CustomUserPrincipal authUser,
                        Integer departmentId,
                        Long managerId,
                        HttpServletRequest request) {

                Department department = departmentRepository.findById(departmentId)
                                .orElseThrow(() -> new RuntimeException("Department not found"));

                User manager = userRepository.findById(managerId)
                                .orElseThrow(() -> new RuntimeException("Manager not found"));

                department.setManager(manager);
                manager.setDepartment(department);
                manager.setRole(UserRole.AGENT);

                userRepository.save(manager);

                Department savedDepartment = departmentRepository.save(department);

                auditLogService.logAction(
                                "DEPARTMENT",
                                Long.valueOf(savedDepartment.getDepartmentId()),
                                authUser.getUser(),
                                AuditAction.ASSIGNED,
                                Map.of(
                                                "entityName", savedDepartment.getName(),
                                                "assignedTo", savedDepartment.getManager().getFullName()),
                                request);

                return DepartmentResponseDto.fromEntity(savedDepartment);
        }

        @Override
        public List<UserResponseDto> getAgentsByDepartment(CustomUserPrincipal authUser, Integer departmentId) {
                List<User> users = userRepository.findUsersByDepartment_Id(departmentId);
                return users.stream()
                                .map(UserResponseDto::fromEntity)
                                .toList();
        }

}
