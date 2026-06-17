package com.backend.helpdeskpro.dto.sla;

import java.time.LocalDateTime;

public class SlaPolicyResponseDto {

    private Long slaPolicyId;

    private String name;
    private Integer responseMinutes;
    private Integer resolutionMinutes;

    private Boolean isActive;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Long getSlaPolicyId() {
        return slaPolicyId;
    }

    public void setSlaPolicyId(Long slaPolicyId) {
        this.slaPolicyId = slaPolicyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getResponseMinutes() {
        return responseMinutes;
    }

    public void setResponseMinutes(Integer responseMinutes) {
        this.responseMinutes = responseMinutes;
    }

    public Integer getResolutionMinutes() {
        return resolutionMinutes;
    }

    public void setResolutionMinutes(Integer resolutionMinutes) {
        this.resolutionMinutes = resolutionMinutes;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    public static SlaPolicyResponseDto fromEntity(com.backend.helpdeskpro.entity.SlaPolicy slaPolicy) {
        SlaPolicyResponseDto dto = new SlaPolicyResponseDto();
        dto.setSlaPolicyId(slaPolicy.getId().longValue());
        dto.setName(slaPolicy.getName());
        dto.setResponseMinutes(slaPolicy.getResponseMinutes());
        dto.setResolutionMinutes(slaPolicy.getResolutionMinutes());
        dto.setIsActive(slaPolicy.getActive());
        dto.setCreatedAt(slaPolicy.getCreatedAt());
        dto.setUpdatedAt(slaPolicy.getUpdatedAt());
        return dto;
    }
    
}