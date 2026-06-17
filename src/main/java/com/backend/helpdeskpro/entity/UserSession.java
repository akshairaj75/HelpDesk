package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_sessions", indexes = {
                @Index(name = "idx_session_user", columnList = "user_id"),
                @Index(name = "idx_session_expires", columnList = "expires_at")
}, uniqueConstraints = @UniqueConstraint(name = "uq_session_token", columnNames = "refresh_token"))
public class UserSession {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "refresh_token", nullable = false, unique = true, length = 500)
        private String refreshToken;

        @Column(name = "ip_address", length = 45)
        private String ipAddress;

        @Column(name = "user_agent", length = 255)
        private String userAgent;

        @Column(name = "expires_at", nullable = false)
        private LocalDateTime expiresAt;

        @Column(name = "revoked", nullable = false)
        private Boolean revoked = false;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

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

        public String getRefreshToken() {
                return refreshToken;
        }

        public void setRefreshToken(String refreshToken) {
                this.refreshToken = refreshToken;
        }

        public String getIpAddress() {
                return ipAddress;
        }

        public void setIpAddress(String ipAddress) {
                this.ipAddress = ipAddress;
        }

        public String getUserAgent() {
                return userAgent;
        }

        public void setUserAgent(String userAgent) {
                this.userAgent = userAgent;
        }

        public LocalDateTime getExpiresAt() {
                return expiresAt;
        }

        public void setExpiresAt(LocalDateTime expiresAt) {
                this.expiresAt = expiresAt;
        }

        public Boolean getRevoked() {
                return revoked;
        }

        public void setRevoked(Boolean revoked) {
                this.revoked = revoked;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
}
