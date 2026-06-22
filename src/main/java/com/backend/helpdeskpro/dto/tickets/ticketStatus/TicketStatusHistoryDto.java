package com.backend.helpdeskpro.dto.tickets.ticketStatus;

import com.backend.helpdeskpro.entity.TicketStatusHistory;
import com.backend.helpdeskpro.enums.TicketStatus;

import java.time.LocalDateTime;

public class TicketStatusHistoryDto {
    private Long ticketId;
    private TicketStatus newStatus;
    private TicketStatus oldStatus;
    private String reason;
    
    public Long getTicketId() {
        return ticketId;
    }
    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }
    public TicketStatus getNewStatus() {
        return newStatus;
    }
    public void setNewStatus(TicketStatus newStatus) {
        this.newStatus = newStatus;
    }
    public TicketStatus getOldStatus() {
        return oldStatus;
    }
    public void setOldStatus(TicketStatus oldStatus) {
        this.oldStatus = oldStatus;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    public static TicketStatusHistoryDto fromEntity(TicketStatusHistory history) {
        
        TicketStatusHistoryDto dto = new TicketStatusHistoryDto();
        dto.setTicketId(history.getTicket().getId());
        dto.setNewStatus(history.getNewStatus());
        dto.setOldStatus(history.getOldStatus());
        dto.setReason(history.getReason());
        return dto;
    }

}
