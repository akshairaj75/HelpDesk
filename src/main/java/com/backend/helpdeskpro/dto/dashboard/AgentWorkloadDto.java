package com.backend.helpdeskpro.dto.dashboard;

import com.backend.helpdeskpro.entity.User;

public class AgentWorkloadDto {

    private Long agentId;
    private String agentName;
    private String agentEmail;
    private Long activeTicketCount;
    private Integer workloadPercentage;

    public AgentWorkloadDto() {
    }

    public AgentWorkloadDto(Long agentId, String agentName, String agentEmail, Long activeTicketCount) {
        this.agentId = agentId;
        this.agentName = agentName;
        this.agentEmail = agentEmail;
        this.activeTicketCount = activeTicketCount;
    }

    public Long getAgentId() {
        return agentId;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentEmail() {
        return agentEmail;
    }

    public void setAgentEmail(String agentEmail) {
        this.agentEmail = agentEmail;
    }

    public Long getActiveTicketCount() {
        return activeTicketCount;
    }

    public void setActiveTicketCount(Long activeTicketCount) {
        this.activeTicketCount = activeTicketCount;
    }

    public Integer getWorkloadPercentage() {
        return workloadPercentage;
    }

    public void setWorkloadPercentage(Integer workloadPercentage) {
        this.workloadPercentage = workloadPercentage;
    }

    public static AgentWorkloadDto fromEntity(User user, Long activeTicketCount) {
        AgentWorkloadDto dto = new AgentWorkloadDto();
        dto.setAgentId(user.getId());
        dto.setAgentName(user.getFullName());
        dto.setAgentEmail(user.getEmail());
        dto.setActiveTicketCount(activeTicketCount);
        return dto;
    }
}