package com.backend.helpdeskpro.dto.tickets.ticketDto;

import java.time.LocalDateTime;
import java.util.List;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.category.CategoryResponseDto;
import com.backend.helpdeskpro.dto.tickets.ticketAttachment.TicketAttachmentDto;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.enums.PriorityLevel;
import com.backend.helpdeskpro.enums.TicketChannel;
import com.backend.helpdeskpro.enums.TicketStatus;

public class TicketResponseDto {

    private Long ticketId;
    private String ticketNo;
    private Long reporterId;
    private String reporterName;
    private Long assigneeId;
    private UserResponseDto assignee;
    // private Long categoryId;
    private CategoryResponseDto category;
    private Long departmentId;
    private String departmentName;
    private String slaPolicyName;
    private Long slaPolicyId;
    private String subject;
    private String description;
    private TicketStatus status;
    private PriorityLevel priority;
    private TicketChannel channel;
    private LocalDateTime slaDueAt;
    private Boolean slaBreached;
    private LocalDateTime firstResponseAt;
    private LocalDateTime resolvedAt;
    private LocalDateTime closedAt;
    private List<TicketAttachmentDto> attachments;

    public List<TicketAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TicketAttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public Long getReporterId() {
        return reporterId;
    }

    public void setReporterId(Long reporterId) {
        this.reporterId = reporterId;
    }

    public String getReporterName() {
        return reporterName;
    }

    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }

    public Long getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Long assigneeId) {
        this.assigneeId = assigneeId;
    }

    public UserResponseDto getAssignee() {
        return assignee;
    }

    public void setAssignee(UserResponseDto assignee) {
        this.assignee = assignee;
    }

    public CategoryResponseDto getCategory() {
        return category;
    }

    public void setCategory(CategoryResponseDto category) {
        this.category = category;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getSlaPolicyName() {
        return slaPolicyName;
    }

    public void setSlaPolicyName(String slaPolicyName) {
        this.slaPolicyName = slaPolicyName;
    }
    public Long getSlaPolicyId() {
        return slaPolicyId;
    }

    public void setSlaPolicyId(Long slaPolicyId) {
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

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
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

    public LocalDateTime getSlaDueAt() {
        return slaDueAt;
    }

    public void setSlaDueAt(LocalDateTime slaDueAt) {
        this.slaDueAt = slaDueAt;
    }

    public Boolean getSlaBreached() {
        return slaBreached;
    }

    public void setSlaBreached(Boolean slaBreached) {
        this.slaBreached = slaBreached;
    }

    public LocalDateTime getFirstResponseAt() {
        return firstResponseAt;
    }

    public void setFirstResponseAt(LocalDateTime firstResponseAt) {
        this.firstResponseAt = firstResponseAt;
    }

    public LocalDateTime getResolvedAt() {
        return resolvedAt;
    }

    public void setResolvedAt(LocalDateTime resolvedAt) {
        this.resolvedAt = resolvedAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    public static TicketResponseDto fromEntity(Ticket ticket) {
        TicketResponseDto dto = new TicketResponseDto();
        dto.setTicketId(ticket.getId());
        dto.setTicketNo(ticket.getTicketNo());
        if (ticket.getReporter() != null) {
            dto.setReporterId(ticket.getReporter().getId());
            dto.setReporterName(ticket.getReporter().getFullName());
        }
        dto.setAssignee(ticket.getAssignee() != null ? UserResponseDto.fromEntity(ticket.getAssignee()) : null);
        dto.setAssigneeId(ticket.getAssignee() != null ? ticket.getAssignee().getId() : null);
        // dto.setCategoryId(ticket.getCategory() != null ? ticket.getCategory().getId().longValue() : null);
        dto.setCategory(ticket.getCategory() != null ? CategoryResponseDto.fromEntity(ticket.getCategory()) : null);

        if (ticket.getDepartment() != null) {
            dto.setDepartmentId(ticket.getDepartment().getDepartmentId().longValue());
            dto.setDepartmentName(ticket.getDepartment().getName());
        }
        if (ticket.getSlaPolicy() != null) {
            dto.setSlaPolicyId(ticket.getSlaPolicy().getId().longValue());
            dto.setSlaPolicyName(ticket.getSlaPolicy().getName());
        }
        dto.setSubject(ticket.getSubject());
        dto.setAttachments(ticket.getAttachments() != null ? ticket.getAttachments().stream()
                .map(TicketAttachmentDto::fromEntity)
                .toList() : null);

        dto.setDescription(ticket.getDescription());
        dto.setStatus(ticket.getStatus());
        dto.setPriority(ticket.getPriority());
        dto.setChannel(ticket.getChannel());
        dto.setSlaDueAt(ticket.getSlaDueAt());
        dto.setSlaBreached(ticket.getSlaBreached());
        dto.setFirstResponseAt(ticket.getFirstResponseAt());
        dto.setResolvedAt(ticket.getResolvedAt());
        dto.setClosedAt(ticket.getClosedAt());
        return dto;
    }

}