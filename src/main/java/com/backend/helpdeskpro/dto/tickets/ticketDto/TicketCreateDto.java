package com.backend.helpdeskpro.dto.tickets.ticketDto;

import java.util.List;

import com.backend.helpdeskpro.enums.PriorityLevel;
import com.backend.helpdeskpro.enums.TicketChannel;

public class TicketCreateDto {
    private Long reporterId;
    private Integer categoryId;
    private Integer departmentId;
    private Long assigneeId;
    private Integer slaPolicyId;
    private String subject;
    private String description;
    private PriorityLevel priority = PriorityLevel.MEDIUM;
    private TicketChannel channel = TicketChannel.WEB;
    private List<Integer> tagIds;

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public Integer getSlaPolicyId() {
        return slaPolicyId;
    }

    public void setSlaPolicyId(Integer slaPolicyId) {
        this.slaPolicyId = slaPolicyId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PriorityLevel getPriority() {
        return priority;
    }

    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public TicketChannel getChannel() {
        return channel;
    }

    public void setChannel(TicketChannel channel) {
        this.channel = channel;
    }

    public List<Integer> getTagIds() {
        return tagIds;
    }

    public void setTagIds(List<Integer> tagIds) {
        this.tagIds = tagIds;
    }

}
