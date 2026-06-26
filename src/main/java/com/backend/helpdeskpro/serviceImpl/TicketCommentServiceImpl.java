package com.backend.helpdeskpro.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentRegisterDto;
import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentResponseDto;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.TicketAttachment;
import com.backend.helpdeskpro.entity.TicketComment;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.enums.NotificationType;
import com.backend.helpdeskpro.repository.TicketAttachmentRepository;
import com.backend.helpdeskpro.repository.TicketCommentRepository;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.NotificationService;
import com.backend.helpdeskpro.service.TicketCommentService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class TicketCommentServiceImpl implements TicketCommentService {

    @Autowired
    TicketCommentRepository ticketCommentRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    NotificationService notificationService;

    @Autowired
    TicketAttachmentRepository attachmentRepository;

    @Autowired
    AuditService auditService;

    @Autowired
    AuditServiceImpl auditLogService;

    @Override
    @Transactional
    public TicketCommentRegisterDto addComment(
            CustomUserPrincipal authUser,
            TicketCommentRegisterDto dto,
            List<MultipartFile> files,
            Long ticketId,
            HttpServletRequest request) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User author = authUser.getUser();

        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setAuthor(author);
        comment.setBody(dto.getBody());
        comment.setInternal(dto.getInternal());

        TicketComment savedComment = ticketCommentRepository.save(comment);

        User receiver = null;

        // CREATING NOTIFICATION FOR ASSIGNEE
        if (ticket.getAssignee() != null && !ticket.getAssignee().getId().equals(authUser.getUserId())) {
            receiver = ticket.getAssignee();
            notificationService.createNotification(
                    receiver,
                    ticket,
                    NotificationType.NEW_COMMENT,
                    "New comment added",
                    author.getFullName() + " commented on ticket " + ticket.getTicketNo(),
                    "/tickets/" + ticket.getId());
        }

        auditLogService.logAction(
                "TICKET_COMMENT",
                savedComment.getId(),
                authUser.getUser(),
                AuditAction.COMMENT_ADDED,
                Map.of(
                        "ticketId", ticketId,
                        "ticketNo", ticket.getTicketNo(),
                        "isInternal", savedComment.getInternal(),
                        "body", savedComment.getBody()),
                request);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (file == null || file.isEmpty()) {
                    continue;
                }
                try {
                    String filePath = fileStorageService.storeFile(file, "comments");
                    TicketAttachment attachment = new TicketAttachment();
                    attachment.setTicket(ticket);
                    attachment.setComment(savedComment);
                    attachment.setUploadedBy(author);
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFileUrl(filePath);
                    attachment.setFileSizeKb((int) (file.getSize() / 1024));
                    attachment.setMimeType(file.getContentType());
                    attachmentRepository.save(attachment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return TicketCommentRegisterDto.fromEntity(savedComment);

    }

    @Override
    public List<TicketCommentResponseDto> getCommentsByTicketId(CustomUserPrincipal authUser, Long ticketId) {
        List<TicketComment> comments = ticketCommentRepository.findByTicketIdOrderByCreatedAtAsc(ticketId);
        return comments.stream()
                .map(TicketCommentResponseDto::fromEntity)
                .toList();

    }

    @Override
    public void addAttachmentToComment(CustomUserPrincipal authUser, Long commentId, List<MultipartFile> files,
            HttpServletRequest request) {
        TicketComment comment = ticketCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        User author = authUser.getUser();

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                try {
                    String filePath = fileStorageService.storeFile(file, "comments");
                    TicketAttachment attachment = new TicketAttachment();
                    attachment.setTicket(comment.getTicket());
                    attachment.setComment(comment);
                    attachment.setUploadedBy(author);
                    attachment.setFileName(file.getOriginalFilename());
                    attachment.setFileUrl(filePath);
                    attachment.setFileSizeKb((int) (file.getSize() / 1024));
                    attachment.setMimeType(file.getContentType());
                    TicketAttachment savedAttachment = attachmentRepository.save(attachment);

                    auditLogService.logAction(
                            "TICKET_ATTACHMENT",
                            savedAttachment.getId(),
                            savedAttachment.getUploadedBy(),
                            AuditAction.ATTACHMENT_UPLOADED,
                            Map.of(
                                    "ticketId", comment.getTicket().getId(),
                                    "ticketNo", comment.getTicket().getTicketNo(),
                                    "fileName", savedAttachment.getFileName(),
                                    "fileUrl", savedAttachment.getFileUrl(),
                                    "mimeType",
                                    savedAttachment.getMimeType() != null ? savedAttachment.getMimeType() : "",
                                    "fileSizeKb",
                                    savedAttachment.getFileSizeKb() != null ? savedAttachment.getFileSizeKb() : 0),
                            request);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return;

    }

    @Override
    public void deleteAttachment(
            Long attachmentId,
            HttpServletRequest request,
            CustomUserPrincipal authUser) {
        TicketAttachment attachment = attachmentRepository.findById(attachmentId)
                .orElseThrow(() -> new RuntimeException("Attachment not found"));
        attachmentRepository.delete(attachment);
        fileStorageService.deleteFile(attachment.getFileUrl());
        auditLogService.logAction(
                "TICKET",
                attachment.getTicket().getId(),
                authUser.getUser(),
                AuditAction.ATTACHMENT_DELETED,
                Map.of(
                        "ticketId", attachment.getTicket().getId(),
                        "ticketNo", attachment.getTicket().getTicketNo(),
                        "fileName", attachment.getFileName()),
                request);
    }

    @Override
    public void deleteComment(Long commentId,
            Long commentId2,
            HttpServletRequest request) {
        TicketComment comment = ticketCommentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        List<TicketAttachment> attachments = attachmentRepository.findByComment(comment);
        for (TicketAttachment attachment : attachments) {
            fileStorageService.deleteFile(attachment.getFileUrl());
            attachmentRepository.delete(attachment);
        }
        ticketCommentRepository.delete(comment);
        auditLogService.logAction(
                "TICKET",
                comment.getTicket().getId(),
                comment.getAuthor(),
                AuditAction.DELETED,
                Map.of(
                        "body", comment.getBody()),
                request);
    }
}
