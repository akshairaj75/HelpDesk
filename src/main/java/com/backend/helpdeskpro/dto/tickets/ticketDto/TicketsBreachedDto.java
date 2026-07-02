package com.backend.helpdeskpro.dto.tickets.ticketDto;

import java.time.LocalDateTime;

import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.enums.PriorityLevel;
import com.backend.helpdeskpro.enums.TicketStatus;

public class TicketsBreachedDto {
    private Long ticketId;
    private String ticketNo;
    private Long reporterId;
    private String reporterName;
    private Long assigneeId;
    private String slaPolicyName;
    private Long slaPolicyId;
    private String subject;
    private String description;
    private TicketStatus status;
    private PriorityLevel priority;
    private LocalDateTime slaDueAt;
    private Boolean slaBreached;

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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }


    public static TicketsBreachedDto fromEntity(Ticket ticket) {
        TicketsBreachedDto dto = new TicketsBreachedDto();
        dto.setTicketId(ticket.getId());
        dto.setTicketNo(ticket.getTicketNo());
        dto.setReporterId(ticket.getReporter().getId());
        dto.setReporterName(ticket.getReporter().getFullName());
        dto.setAssigneeId(ticket.getAssignee().getId());
        dto.setSlaPolicyName(ticket.getSlaPolicy().getName());
        dto.setSlaPolicyId(ticket.getSlaPolicy().getId().longValue());
        dto.setSubject(ticket.getSubject());
        dto.setDescription(ticket.getDescription());
        dto.setStatus(ticket.getStatus());
        dto.setPriority(ticket.getPriority());
        dto.setSlaDueAt(ticket.getSlaDueAt());
        dto.setSlaBreached(ticket.getSlaBreached());
        return dto;
    }

}
