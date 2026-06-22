package com.backend.helpdeskpro.controller.ticket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentRegisterDto;
import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketCommentService;

import jakarta.servlet.http.HttpServletRequest;

@RequestMapping("/api/helpdesk/tickets/comment")
@RestController
public class TicketCommentController {

    @Autowired
    TicketCommentService ticketCommentService;

    @PostMapping("/{ticketId}/add-comment")
    public ResponseEntity<TicketCommentRegisterDto> addComment(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId,
            @RequestPart("data") TicketCommentRegisterDto dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files,
            HttpServletRequest request) {
        TicketCommentRegisterDto resp = ticketCommentService.addComment(authUser, dto, files, ticketId, request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("/{ticketId}/get-comments")
    public ResponseEntity<List<TicketCommentResponseDto>> getCommentsByTicketId(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long ticketId) {
        List<TicketCommentResponseDto> comments = ticketCommentService.getCommentsByTicketId(authUser, ticketId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/{commentId}/add-attachment")
    public ResponseEntity<TicketCommentRegisterDto> addAttachmentToComment(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long commentId,
            @RequestPart("files") List<MultipartFile> files,
             HttpServletRequest request) {

        ticketCommentService.addAttachmentToComment(authUser, commentId, files, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/attachment/{attachmentId}/delete")
    public ResponseEntity<Void> deleteAttachment(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long attachmentId,
         HttpServletRequest request) {
        ticketCommentService.deleteAttachment(attachmentId,request,authUser);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{commentId}/delete")
    public ResponseEntity<Void> deleteComment(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long commentId,
         HttpServletRequest request) {

        ticketCommentService.deleteComment(commentId, commentId, request);
        return ResponseEntity.noContent().build();
    }

}
