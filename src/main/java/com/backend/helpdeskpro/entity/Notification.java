package com.backend.helpdeskpro.entity;

import com.backend.helpdeskpro.enums.NotificationChannel;
import com.backend.helpdeskpro.enums.NotificationType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "notifications", indexes = {
                @Index(name = "idx_notif_user", columnList = "user_id"),
                @Index(name = "idx_notif_ticket", columnList = "ticket_id"),
                @Index(name = "idx_notif_type", columnList = "type"),
                @Index(name = "idx_notif_read", columnList = "is_read"),
                @Index(name = "idx_notif_sent", columnList = "sent_at"),
                @Index(name = "idx_notif_user_unread", columnList = "user_id, is_read, sent_at")
})
public class Notification {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "ticket_id")
        private Ticket ticket;

        @Enumerated(EnumType.STRING)
        @Column(name = "type", nullable = false, length = 30)
        private NotificationType type = NotificationType.SYSTEM;

        @Enumerated(EnumType.STRING)
        @Column(name = "channel", nullable = false, length = 20)
        private NotificationChannel channel = NotificationChannel.IN_APP;

        @Column(name = "title", nullable = false, length = 255)
        private String title;

        @Column(name = "body", columnDefinition = "TEXT")
        private String body;

        @Column(name = "deep_link", length = 255)
        private String deepLink;

        @Column(name = "is_read", nullable = false)
        private Boolean read = false;

        @CreationTimestamp
        @Column(name = "sent_at", nullable = false, updatable = false)
        private LocalDateTime sentAt;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public Ticket getTicket() {
                return ticket;
        }

        public void setTicket(Ticket ticket) {
                this.ticket = ticket;
        }

        public NotificationType getType() {
                return type;
        }

        public void setType(NotificationType type) {
                this.type = type;
        }

        public NotificationChannel getChannel() {
                return channel;
        }

        public void setChannel(NotificationChannel channel) {
                this.channel = channel;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getBody() {
                return body;
        }

        public void setBody(String body) {
                this.body = body;
        }

        public String getDeepLink() {
                return deepLink;
        }

        public void setDeepLink(String deepLink) {
                this.deepLink = deepLink;
        }

        public Boolean getRead() {
                return read;
        }

        public void setRead(Boolean read) {
                this.read = read;
        }

        public LocalDateTime getSentAt() {
                return sentAt;
        }

        public void setSentAt(LocalDateTime sentAt) {
                this.sentAt = sentAt;
        }
}
