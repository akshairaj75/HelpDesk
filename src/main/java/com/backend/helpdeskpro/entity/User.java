package com.backend.helpdeskpro.entity;

import com.backend.helpdeskpro.enums.AuthProvider;
import com.backend.helpdeskpro.enums.UserRole;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users", indexes = {
                @Index(name = "idx_users_role", columnList = "role"),
                @Index(name = "idx_users_dept", columnList = "department_id"),
                @Index(name = "idx_users_active", columnList = "is_active"),
                @Index(name = "idx_users_supervisor", columnList = "supervisor_id")
}, uniqueConstraints = @UniqueConstraint(name = "uq_users_email", columnNames = "email"))
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "full_name", nullable = false, length = 100)
        private String fullName;

        @Column(name = "email", nullable = false, unique = true, length = 150)
        private String email;

        @Column(name = "phone", length = 20)
        private String phone;

        @Column(name = "password_hash", length = 255)
        private String passwordHash;

        @Enumerated(EnumType.STRING)
        @Column(name = "role", nullable = false, length = 20)
        private UserRole role = UserRole.STAFF;

        @Column(name = "avatar_url", length = 255)
        private String avatarUrl;

        @Column(name = "fcm_token", length = 255)
        private String fcmToken;

        @Column(name = "provider_id", length = 255) // recently added for social login
        private String providerId;

        @Enumerated(EnumType.STRING) // recently added for social login
        @Column(name = "auth_provider", nullable = false)
        private AuthProvider authProvider = AuthProvider.LOCAL;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "department_id")
        private Department department;

        @ManyToOne(fetch = FetchType.LAZY) // recently added for supervisor
        @JoinColumn(name = "supervisor_id")
        private User supervisor;

        @OneToMany(mappedBy = "supervisor")
        private List<User> subordinates = new ArrayList<>(); // recently added for team lead to get subordinates

        @Column(name = "is_active", nullable = false)
        private Boolean active = false;

        @Column(name = "last_login_at")
        private LocalDateTime lastLoginAt;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "manager")
        private List<Department> managedDepartments = new ArrayList<>();

        @OneToMany(mappedBy = "reporter")
        private List<Ticket> reportedTickets = new ArrayList<>();

        @OneToMany(mappedBy = "assignee")
        private List<Ticket> assignedTickets = new ArrayList<>();

        @OneToMany(mappedBy = "defaultAssignee")
        private List<Category> defaultAssignedCategories = new ArrayList<>();

        @OneToMany(mappedBy = "author")
        private List<TicketComment> comments = new ArrayList<>();

        @OneToMany(mappedBy = "uploadedBy")
        private List<TicketAttachment> uploadedAttachments = new ArrayList<>();

        @OneToMany(mappedBy = "changedBy")
        private List<TicketStatusHistory> statusChanges = new ArrayList<>();

        @OneToMany(mappedBy = "author")
        private List<KbArticle> articles = new ArrayList<>();

        @OneToMany(mappedBy = "user")
        private List<KbArticleRating> articleRatings = new ArrayList<>();

        @OneToMany(mappedBy = "user")
        private List<Notification> notifications = new ArrayList<>();

        @OneToMany(mappedBy = "actor")
        private List<AuditLog> auditLogs = new ArrayList<>();

        @OneToMany(mappedBy = "user")
        private List<UserSession> sessions = new ArrayList<>();

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getFullName() {
                return fullName;
        }

        public void setFullName(String fullName) {
                this.fullName = fullName;
        }

        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
        }

        public String getPhone() {
                return phone;
        }

        public void setPhone(String phone) {
                this.phone = phone;
        }

        public String getPasswordHash() {
                return passwordHash;
        }

        public void setPasswordHash(String passwordHash) {
                this.passwordHash = passwordHash;
        }

        public UserRole getRole() {
                return role;
        }

        public void setRole(UserRole role) {
                this.role = role;
        }

        public String getAvatarUrl() {
                return avatarUrl;
        }

        public void setAvatarUrl(String avatarUrl) {
                this.avatarUrl = avatarUrl;
        }

        public String getFcmToken() {
                return fcmToken;
        }

        public void setFcmToken(String fcmToken) {
                this.fcmToken = fcmToken;
        }

        public Department getDepartment() {
                return department;
        }

        public User getSupervisor() {
                return supervisor;
        }

        public void setSupervisor(User supervisor) {
                this.supervisor = supervisor;
        }

        public List<User> getSubordinates() {
                return subordinates;
        }

        public void setSubordinates(List<User> subordinates) {
                this.subordinates = subordinates;
        }

        public String getProviderId() {
                return providerId;
        }

        public void setProviderId(String providerId) {
                this.providerId = providerId;
        }

        public AuthProvider getAuthProvider() {
                return authProvider;
        }

        public void setAuthProvider(AuthProvider authProvider) {
                this.authProvider = authProvider;
        }

        public void setDepartment(Department department) {
                this.department = department;
        }

        public Boolean getActive() {
                return active;
        }

        public void setActive(Boolean active) {
                this.active = active;
        }

        public LocalDateTime getLastLoginAt() {
                return lastLoginAt;
        }

        public void setLastLoginAt(LocalDateTime lastLoginAt) {
                this.lastLoginAt = lastLoginAt;
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

        public List<Department> getManagedDepartments() {
                return managedDepartments;
        }

        public void setManagedDepartments(List<Department> managedDepartments) {
                this.managedDepartments = managedDepartments;
        }

        public List<Ticket> getReportedTickets() {
                return reportedTickets;
        }

        public void setReportedTickets(List<Ticket> reportedTickets) {
                this.reportedTickets = reportedTickets;
        }

        public List<Ticket> getAssignedTickets() {
                return assignedTickets;
        }

        public void setAssignedTickets(List<Ticket> assignedTickets) {
                this.assignedTickets = assignedTickets;
        }

        public List<Category> getDefaultAssignedCategories() {
                return defaultAssignedCategories;
        }

        public void setDefaultAssignedCategories(List<Category> defaultAssignedCategories) {
                this.defaultAssignedCategories = defaultAssignedCategories;
        }

        public List<TicketComment> getComments() {
                return comments;
        }

        public void setComments(List<TicketComment> comments) {
                this.comments = comments;
        }

        public List<TicketAttachment> getUploadedAttachments() {
                return uploadedAttachments;
        }

        public void setUploadedAttachments(List<TicketAttachment> uploadedAttachments) {
                this.uploadedAttachments = uploadedAttachments;
        }

        public List<TicketStatusHistory> getStatusChanges() {
                return statusChanges;
        }

        public void setStatusChanges(List<TicketStatusHistory> statusChanges) {
                this.statusChanges = statusChanges;
        }

        public List<KbArticle> getArticles() {
                return articles;
        }

        public void setArticles(List<KbArticle> articles) {
                this.articles = articles;
        }

        public List<KbArticleRating> getArticleRatings() {
                return articleRatings;
        }

        public void setArticleRatings(List<KbArticleRating> articleRatings) {
                this.articleRatings = articleRatings;
        }

        public List<Notification> getNotifications() {
                return notifications;
        }

        public void setNotifications(List<Notification> notifications) {
                this.notifications = notifications;
        }

        public List<AuditLog> getAuditLogs() {
                return auditLogs;
        }

        public void setAuditLogs(List<AuditLog> auditLogs) {
                this.auditLogs = auditLogs;
        }

        public List<UserSession> getSessions() {
                return sessions;
        }

        public void setSessions(List<UserSession> sessions) {
                this.sessions = sessions;
        }
}