package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import com.backend.helpdeskpro.enums.AuditAction;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs", indexes = {
                @Index(name = "idx_audit_entity", columnList = "entity_type, entity_id"),
                @Index(name = "idx_audit_actor", columnList = "performed_by"),
                @Index(name = "idx_audit_action", columnList = "action"),
                @Index(name = "idx_audit_created", columnList = "created_at")
})
public class AuditLog {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "entity_type", nullable = false, length = 50)
        private String entityType;

        @Column(name = "entity_id", nullable = false)
        private Long entityId;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "performed_by")
        private User actor;

        @Column(name = "action", nullable = false, length = 50)
        private AuditAction action;

        @Column(name = "payload", columnDefinition = "JSON")
        private String payload;

        @Column(name = "ip_address", length = 45)
        private String ipAddress;

        @Column(name = "user_agent", length = 255)
        private String userAgent;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getEntityType() {
                return entityType;
        }

        public void setEntityType(String entityType) {
                this.entityType = entityType;
        }

        public Long getEntityId() {
                return entityId;
        }

        public void setEntityId(Long entityId) {
                this.entityId = entityId;
        }

        public User getActor() {
                return actor;
        }

        public void setActor(User actor) {
                this.actor = actor;
        }

        public AuditAction getAction() {
                return action;
        }

        public void setAction(AuditAction action) {
                this.action = action;
        }

        public String getPayload() {
                return payload;
        }

        public void setPayload(String payload) {
                this.payload = payload;
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

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
}
