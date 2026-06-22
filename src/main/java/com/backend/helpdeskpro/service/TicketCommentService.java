package com.backend.helpdeskpro.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentRegisterDto;
import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface TicketCommentService {

    TicketCommentRegisterDto addComment(CustomUserPrincipal authUser, TicketCommentRegisterDto dto,
            List<MultipartFile> files, Long ticketId, HttpServletRequest request);

    List<TicketCommentResponseDto> getCommentsByTicketId(CustomUserPrincipal authUser, Long ticketId);

    void addAttachmentToComment(CustomUserPrincipal authUser, Long commentId, List<MultipartFile> files, HttpServletRequest request);

    void deleteAttachment(Long attachmentId, HttpServletRequest request, CustomUserPrincipal authUser);

    void deleteComment(Long commentId, Long commentId2, HttpServletRequest request);

}
