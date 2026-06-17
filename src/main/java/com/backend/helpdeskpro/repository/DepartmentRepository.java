package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {

    Optional<Department> findByNameIgnoreCase(String name);
}
