package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.TicketAttachment;
import com.backend.helpdeskpro.entity.TicketComment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketAttachmentRepository extends JpaRepository<TicketAttachment, Long> {

    List<TicketAttachment> findByTicketId(Long ticketId);

    List<TicketAttachment> findByComment(TicketComment comment);

    List<TicketAttachment> findByTicketIdAndCommentIsNull(Long id);

}
