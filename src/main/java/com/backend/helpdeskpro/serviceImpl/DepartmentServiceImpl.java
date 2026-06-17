package com.backend.helpdeskpro.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.department.DepartmentCreateDto;
import com.backend.helpdeskpro.dto.department.DepartmentResponseDto;
import com.backend.helpdeskpro.entity.Department;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.repository.DepartmentRepository;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.DepartmentService;

import jakarta.transaction.Transactional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    @Override
    public DepartmentResponseDto createDepartment(CustomUserPrincipal authUser, DepartmentCreateDto dto) {

        User manager = userRepository.findById(authUser.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Department department = new Department();
        department.setName(dto.getName());
        department.setDescription(dto.getDescription());
        department.setActive(dto.getIsActive());
        department.setManager(manager);

        departmentRepository.save(department);

        return DepartmentResponseDto.fromEntity(department);

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

}
