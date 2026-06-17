package com.backend.helpdeskpro.entity;

import com.backend.helpdeskpro.enums.TicketStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket_status_history", indexes = {
                @Index(name = "idx_tsh_ticket", columnList = "ticket_id"),
                @Index(name = "idx_tsh_actor", columnList = "changed_by"),
                @Index(name = "idx_tsh_changed_at", columnList = "changed_at")
})
public class TicketStatusHistory {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "ticket_id", nullable = false)
        private Ticket ticket;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "changed_by", nullable = false)
        private User changedBy;

        @Enumerated(EnumType.STRING)
        @Column(name = "old_status", length = 20)
        private TicketStatus oldStatus;

        @Enumerated(EnumType.STRING)
        @Column(name = "new_status", nullable = false, length = 20)
        private TicketStatus newStatus;

        @Column(name = "reason", length = 500)
        private String reason;

        @CreationTimestamp
        @Column(name = "changed_at", nullable = false, updatable = false)
        private LocalDateTime changedAt;

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

        public User getChangedBy() {
                return changedBy;
        }

        public void setChangedBy(User changedBy) {
                this.changedBy = changedBy;
        }

        public TicketStatus getOldStatus() {
                return oldStatus;
        }

        public void setOldStatus(TicketStatus oldStatus) {
                this.oldStatus = oldStatus;
        }

        public TicketStatus getNewStatus() {
                return newStatus;
        }

        public void setNewStatus(TicketStatus newStatus) {
                this.newStatus = newStatus;
        }

        public String getReason() {
                return reason;
        }

        public void setReason(String reason) {
                this.reason = reason;
        }

        public LocalDateTime getChangedAt() {
                return changedAt;
        }

        public void setChangedAt(LocalDateTime changedAt) {
                this.changedAt = changedAt;
        }
}
