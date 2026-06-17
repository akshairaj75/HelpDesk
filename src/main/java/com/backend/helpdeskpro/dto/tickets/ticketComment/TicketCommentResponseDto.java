package com.backend.helpdeskpro.dto.tickets.ticketComment;

import java.util.ArrayList;
import java.util.List;

import com.backend.helpdeskpro.dto.auth.UserResponseDto;
import com.backend.helpdeskpro.dto.tickets.ticketAttachment.TicketAttachmentDto;
import com.backend.helpdeskpro.entity.TicketComment;

public class TicketCommentResponseDto {
    private Long commentId;
    private UserResponseDto author;
    private String body;
    private Boolean isInternal;
    private List<TicketAttachmentDto> attachments = new ArrayList<>();

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public UserResponseDto getAuthor() {
        return author;
    }

    public void setAuthor(UserResponseDto author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIsInternal() {
        return isInternal;
    }

    public void setIsInternal(Boolean isInternal) {
        this.isInternal = isInternal;
    }

    public List<TicketAttachmentDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<TicketAttachmentDto> attachments) {
        this.attachments = attachments;
    }

    public static TicketCommentResponseDto fromEntity(TicketComment comment) {
        TicketCommentResponseDto dto = new TicketCommentResponseDto();
        dto.setCommentId(comment.getId());
        dto.setAuthor(comment.getAuthor() != null ? UserResponseDto.fromEntity(comment.getAuthor()) : null);
        dto.setBody(comment.getBody());
        dto.setIsInternal(comment.getInternal());
        if (comment.getAttachments() != null) {
            dto.setAttachments(comment.getAttachments().stream()
                    .map(TicketAttachmentDto::fromEntity)
                    .toList());
        }
        return dto;
    }

}
