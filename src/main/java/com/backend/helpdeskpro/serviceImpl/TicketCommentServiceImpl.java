package com.backend.helpdeskpro.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentRegisterDto;
import com.backend.helpdeskpro.dto.tickets.ticketComment.TicketCommentResponseDto;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.TicketAttachment;
import com.backend.helpdeskpro.entity.TicketComment;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.repository.TicketAttachmentRepository;
import com.backend.helpdeskpro.repository.TicketCommentRepository;
import com.backend.helpdeskpro.repository.TicketRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.TicketCommentService;

@Service
public class TicketCommentServiceImpl implements TicketCommentService {

    @Autowired
    TicketCommentRepository ticketCommentRepository;

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    FileStorageService fileStorageService;

    @Autowired
    TicketAttachmentRepository attachmentRepository;

    @Override
    public TicketCommentRegisterDto addComment(CustomUserPrincipal authUser, TicketCommentRegisterDto dto,
            List<MultipartFile> files) {
        Ticket ticket = ticketRepository.findById(dto.getTicketId())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        User author = authUser.getUser();

        TicketComment comment = new TicketComment();
        comment.setTicket(ticket);
        comment.setAuthor(author);
        comment.setBody(dto.getBody());
        comment.setInternal(dto.getInternal());

        TicketComment savedComment = ticketCommentRepository.save(comment);

        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
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
    public void addAttachmentToComment(CustomUserPrincipal authUser, Long commentId, List<MultipartFile> files) {
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
                    attachmentRepository.save(attachment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ;
        
    }



}
