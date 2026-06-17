package com.backend.helpdeskpro.dto.tickets.ticketComment;

import com.backend.helpdeskpro.entity.TicketComment;

public class TicketCommentRegisterDto {
    private Long ticketCommendId;
    private Long ticketId;
    private Long authorId;
    private String body;
    private Boolean internal = false;


    public Long getTicketCommendId() {
        return ticketCommendId;
    }

    public void setTicketCommendId(Long ticketCommendId) {
        this.ticketCommendId = ticketCommendId;
    }

  
    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getInternal() {
        return internal;
    }

    public void setInternal(Boolean internal) {
        this.internal = internal;
    }

    public static TicketCommentRegisterDto fromEntity(TicketComment comment) {
        TicketCommentRegisterDto dto = new TicketCommentRegisterDto();
        dto.setTicketCommendId(comment.getId());
        dto.setTicketId(comment.getTicket() != null ? comment.getTicket().getId() : null);
        dto.setAuthorId(comment.getAuthor() != null ? comment.getAuthor().getId() : null);
        dto.setBody(comment.getBody());
        dto.setInternal(comment.getInternal());
        return dto;
    }

}
