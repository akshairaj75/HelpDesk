package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_attachments", indexes = {
                @Index(name = "idx_attach_ticket", columnList = "ticket_id"),
                @Index(name = "idx_attach_comment", columnList = "comment_id"),
                @Index(name = "idx_attach_uploader", columnList = "uploaded_by")
})
public class TicketAttachment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "ticket_id", nullable = false)
        private Ticket ticket;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "comment_id")
        private TicketComment comment;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "uploaded_by", nullable = false)
        private User uploadedBy;

        @Column(name = "file_name", nullable = false, length = 255)
        private String fileName;

        @Column(name = "file_url", nullable = false, length = 500)
        private String fileUrl;

        @Column(name = "mime_type", length = 100)
        private String mimeType;

        @Column(name = "file_size_kb")
        private Integer fileSizeKb;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

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

        public TicketComment getComment() {
                return comment;
        }

        public void setComment(TicketComment comment) {
                this.comment = comment;
        }

        public User getUploadedBy() {
                return uploadedBy;
        }

        public void setUploadedBy(User uploadedBy) {
                this.uploadedBy = uploadedBy;
        }

        public String getFileName() {
                return fileName;
        }

        public void setFileName(String fileName) {
                this.fileName = fileName;
        }

        public String getFileUrl() {
                return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
                this.fileUrl = fileUrl;
        }

        public String getMimeType() {
                return mimeType;
        }

        public void setMimeType(String mimeType) {
                this.mimeType = mimeType;
        }

        public Integer getFileSizeKb() {
                return fileSizeKb;
        }

        public void setFileSizeKb(Integer fileSizeKb) {
                this.fileSizeKb = fileSizeKb;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
}
