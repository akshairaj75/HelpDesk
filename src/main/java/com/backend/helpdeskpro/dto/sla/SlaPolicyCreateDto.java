package com.backend.helpdeskpro.dto.sla;

import com.backend.helpdeskpro.enums.PriorityLevel;

public class SlaPolicyCreateDto {

    private String name;
    private PriorityLevel priorityLevel;
    private String description;
    private boolean escalateEnabled;
    private Integer escalationMinutes;
    private Integer responseMinutes;
    private Integer resolutionMinutes;
    private Boolean isActive = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PriorityLevel getPriorityLevel() {
        return priorityLevel;
    }

    public void setPriorityLevel(PriorityLevel priorityLevel) {
        this.priorityLevel = priorityLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isEscalateEnabled() {
        return escalateEnabled;
    }

    public void setEscalateEnabled(boolean escalateEnabled) {
        this.escalateEnabled = escalateEnabled;
    }

    public Integer getEscalationMinutes() {
        return escalationMinutes;
    }

    public void setEscalationMinutes(Integer escalationMinutes) {
        this.escalationMinutes = escalationMinutes;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

}