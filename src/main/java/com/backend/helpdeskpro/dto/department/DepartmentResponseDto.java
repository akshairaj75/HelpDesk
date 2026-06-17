package com.backend.helpdeskpro.dto.department;

import com.backend.helpdeskpro.entity.Department;

public class DepartmentResponseDto {

    private Integer departmentId;
    private String name;
    private String description;
    private Long managerId;
    private Boolean isActive;

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public static DepartmentResponseDto fromEntity(Department department) {

        DepartmentResponseDto dto = new DepartmentResponseDto();

        dto.setDepartmentId(department.getDepartmentId());
        dto.setName(department.getName());
        dto.setDescription(department.getDescription());
        dto.setIsActive(department.getActive());
        dto.setManagerId(department.getManager().getId());
        return dto;
    }

}