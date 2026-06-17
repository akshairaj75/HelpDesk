package com.backend.helpdeskpro.entity;

import com.backend.helpdeskpro.enums.PriorityLevel;
import com.backend.helpdeskpro.enums.TicketChannel;
import com.backend.helpdeskpro.enums.TicketStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tickets", indexes = {
                @Index(name = "idx_tickets_reporter", columnList = "reporter_id"),
                @Index(name = "idx_tickets_assignee", columnList = "assignee_id"),
                @Index(name = "idx_tickets_category", columnList = "category_id"),
                @Index(name = "idx_tickets_dept", columnList = "department_id"),
                @Index(name = "idx_tickets_sla", columnList = "sla_policy_id"),
                @Index(name = "idx_tickets_status", columnList = "status"),
                @Index(name = "idx_tickets_priority", columnList = "priority"),
                @Index(name = "idx_tickets_sla_due", columnList = "sla_due_at"),
                @Index(name = "idx_tickets_breached", columnList = "sla_breached"),
                @Index(name = "idx_tickets_dept_priority", columnList = "department_id, priority, status"),
                @Index(name = "idx_tickets_agent_queue", columnList = "assignee_id, status, sla_due_at"),
                @Index(name = "idx_tickets_sla_scheduler", columnList = "sla_due_at, sla_breached, status")
}, uniqueConstraints = @UniqueConstraint(name = "uq_ticket_no", columnNames = "ticket_no"))
public class Ticket {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "ticket_no", nullable = false, unique = true, length = 25)
        private String ticketNo;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "reporter_id", nullable = false)
        private User reporter;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "assignee_id")
        private User assignee;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "category_id", nullable = false)
        private Category category;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "department_id")
        private Department department;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "sla_policy_id")
        private SlaPolicy slaPolicy;

        @Column(name = "subject", nullable = false, length = 255)
        private String subject;

        @Column(name = "description", nullable = false, columnDefinition = "TEXT")
        private String description;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        private TicketStatus status = TicketStatus.OPEN;

        @Enumerated(EnumType.STRING)
        @Column(name = "priority", nullable = false, length = 20)
        private PriorityLevel priority = PriorityLevel.MEDIUM;

        @Enumerated(EnumType.STRING)
        @Column(name = "channel", nullable = false, length = 20)
        private TicketChannel channel = TicketChannel.WEB;

        @Column(name = "sla_due_at")
        private LocalDateTime slaDueAt;

        @Column(name = "sla_breached", nullable = false)
        private Boolean slaBreached = false;

        @Column(name = "sla_breach_notified", nullable = false)
        private Boolean slaBreachNotified = false;

        @Column(name = "first_response_at")
        private LocalDateTime firstResponseAt;

        @Column(name = "resolved_at")
        private LocalDateTime resolvedAt;

        @Column(name = "closed_at")
        private LocalDateTime closedAt;

        @Column(name = "csat_score")
        private Integer csatScore;

        @Column(name = "csat_feedback", length = 500)
        private String csatFeedback;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "ticket")
        private List<TicketComment> comments = new ArrayList<>();

        @OneToMany(mappedBy = "ticket")
        private List<TicketAttachment> attachments = new ArrayList<>();

        @OneToMany(mappedBy = "ticket")
        private List<TicketStatusHistory> statusHistory = new ArrayList<>();

        @ManyToMany
        @JoinTable(name = "ticket_tag_map", joinColumns = @JoinColumn(name = "ticket_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
        private List<TicketTag> tags = new ArrayList<>();

        @OneToMany(mappedBy = "ticket")
        private List<TicketTagMap> ticketTagMaps = new ArrayList<>();

        @OneToMany(mappedBy = "ticket")
        private List<Notification> notifications = new ArrayList<>();

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public String getTicketNo() {
                return ticketNo;
        }

        public void setTicketNo(String ticketNo) {
                this.ticketNo = ticketNo;
        }

        public User getReporter() {
                return reporter;
        }

        public void setReporter(User reporter) {
                this.reporter = reporter;
        }

        public User getAssignee() {
                return assignee;
        }

        public void setAssignee(User assignee) {
                this.assignee = assignee;
        }

        public Category getCategory() {
                return category;
        }

        public void setCategory(Category category) {
                this.category = category;
        }

        public Department getDepartment() {
                return department;
        }

        public void setDepartment(Department department) {
                this.department = department;
        }

        public SlaPolicy getSlaPolicy() {
                return slaPolicy;
        }

        public void setSlaPolicy(SlaPolicy slaPolicy) {
                this.slaPolicy = slaPolicy;
        }

        public String getSubject() {
                return subject;
        }

        public void setSubject(String subject) {
                this.subject = subject;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public TicketStatus getStatus() {
                return status;
        }

        public void setStatus(TicketStatus status) {
                this.status = status;
        }

        public PriorityLevel getPriority() {
                return priority;
        }

        public void setPriority(PriorityLevel priority) {
                this.priority = priority;
        }

        public TicketChannel getChannel() {
                return channel;
        }

        public void setChannel(TicketChannel channel) {
                this.channel = channel;
        }

        public LocalDateTime getSlaDueAt() {
                return slaDueAt;
        }

        public void setSlaDueAt(LocalDateTime slaDueAt) {
                this.slaDueAt = slaDueAt;
        }

        public Boolean getSlaBreached() {
                return slaBreached;
        }

        public void setSlaBreached(Boolean slaBreached) {
                this.slaBreached = slaBreached;
        }

        public Boolean getSlaBreachNotified() {
                return slaBreachNotified;
        }

        public void setSlaBreachNotified(Boolean slaBreachNotified) {
                this.slaBreachNotified = slaBreachNotified;
        }

        public LocalDateTime getFirstResponseAt() {
                return firstResponseAt;
        }

        public void setFirstResponseAt(LocalDateTime firstResponseAt) {
                this.firstResponseAt = firstResponseAt;
        }

        public LocalDateTime getResolvedAt() {
                return resolvedAt;
        }

        public void setResolvedAt(LocalDateTime resolvedAt) {
                this.resolvedAt = resolvedAt;
        }

        public LocalDateTime getClosedAt() {
                return closedAt;
        }

        public void setClosedAt(LocalDateTime closedAt) {
                this.closedAt = closedAt;
        }

        public Integer getCsatScore() {
                return csatScore;
        }

        public void setCsatScore(Integer csatScore) {
                this.csatScore = csatScore;
        }

        public String getCsatFeedback() {
                return csatFeedback;
        }

        public void setCsatFeedback(String csatFeedback) {
                this.csatFeedback = csatFeedback;
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

        public List<TicketComment> getComments() {
                return comments;
        }

        public void setComments(List<TicketComment> comments) {
                this.comments = comments;
        }

        public List<TicketAttachment> getAttachments() {
                return attachments;
        }

        public void setAttachments(List<TicketAttachment> attachments) {
                this.attachments = attachments;
        }

        public List<TicketStatusHistory> getStatusHistory() {
                return statusHistory;
        }

        public void setStatusHistory(List<TicketStatusHistory> statusHistory) {
                this.statusHistory = statusHistory;
        }

        public List<TicketTag> getTags() {
                return tags;
        }

        public void setTags(List<TicketTag> tags) {
                this.tags = tags;
        }

        public List<TicketTagMap> getTicketTagMaps() {
                return ticketTagMaps;
        }

        public void setTicketTagMaps(List<TicketTagMap> ticketTagMaps) {
                this.ticketTagMaps = ticketTagMaps;
        }

        public List<Notification> getNotifications() {
                return notifications;
        }

        public void setNotifications(List<Notification> notifications) {
                this.notifications = notifications;
        }
}
