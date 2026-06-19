package com.backend.helpdeskpro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentRegisterDto;
import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface TicketCommentService {

    TicketCommentRegisterDto addComment(CustomUserPrincipal authUser, TicketCommentRegisterDto dto,
            List<MultipartFile> files);

    List<TicketCommentResponseDto> getCommentsByTicketId(CustomUserPrincipal authUser, Long ticketId);

    void addAttachmentToComment(CustomUserPrincipal authUser, Long commentId, List<MultipartFile> files);

    void deleteAttachment(Long attachmentId);

    void deleteComment(Long commentId);

}
