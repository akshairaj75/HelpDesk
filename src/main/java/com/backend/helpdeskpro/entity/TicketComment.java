package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ticket_comments", indexes = {
                @Index(name = "idx_comments_ticket", columnList = "ticket_id"),
                @Index(name = "idx_comments_author", columnList = "author_id"),
                @Index(name = "idx_comments_created", columnList = "created_at")
})
public class TicketComment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "ticket_id", nullable = false)
        private Ticket ticket;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "author_id", nullable = false)
        private User author;

        @Column(name = "body", nullable = false, columnDefinition = "TEXT")
        private String body;

        @Column(name = "is_internal", nullable = false)
        private Boolean internal = false;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "comment")
        private List<TicketAttachment> attachments = new ArrayList<>();

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public Ticket getTicket() {
                return ticket;
        }

        public void setTicket(Ticket ticket) {
                this.ticket = ticket;
        }

        public User getAuthor() {
                return author;
        }

        public void setAuthor(User author) {
                this.author = author;
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

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }

        public LocalDateTime getUpdatedAt() {
                return updatedAt;
        }

        public void setUpdatedAt(LocalDateTime updatedAt) {
                this.updatedAt = updatedAt;
        }

        public List<TicketAttachment> getAttachments() {
                return attachments;
        }

        public void setAttachments(List<TicketAttachment> attachments) {
                this.attachments = attachments;
        }
}
