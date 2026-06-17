package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories", indexes = {
                @Index(name = "idx_cat_parent", columnList = "parent_id"),
                @Index(name = "idx_cat_active", columnList = "is_active"),
                @Index(name = "idx_cat_sort", columnList = "sort_order")
})
public class Category {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "parent_id")
        private Category parent;

        @OneToMany(mappedBy = "parent")
        private List<Category> children = new ArrayList<>();

        @Column(name = "name", nullable = false, length = 100)
        private String name;

        @Column(name = "description", length = 255)
        private String description;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "default_assignee_id")
        private User defaultAssignee;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "default_sla_id")
        private SlaPolicy defaultSla;

        @Column(name = "icon_slug", length = 60)
        private String iconSlug;

        @Column(name = "sort_order", nullable = false)
        private Integer sortOrder = 0;

        @Column(name = "is_active", nullable = false)
        private Boolean active = true;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "category")
        private List<Ticket> tickets = new ArrayList<>();

        @OneToMany(mappedBy = "category")
        private List<KbArticle> articles = new ArrayList<>();

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public Category getParent() {
                return parent;
        }

        public void setParent(Category parent) {
                this.parent = parent;
        }

        public List<Category> getChildren() {
                return children;
        }

        public void setChildren(List<Category> children) {
                this.children = children;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public User getDefaultAssignee() {
                return defaultAssignee;
        }

        public void setDefaultAssignee(User defaultAssignee) {
                this.defaultAssignee = defaultAssignee;
        }

        public SlaPolicy getDefaultSla() {
                return defaultSla;
        }

        public void setDefaultSla(SlaPolicy defaultSla) {
                this.defaultSla = defaultSla;
        }

        public String getIconSlug() {
                return iconSlug;
        }

        public void setIconSlug(String iconSlug) {
                this.iconSlug = iconSlug;
        }

        public Integer getSortOrder() {
                return sortOrder;
        }

        public void setSortOrder(Integer sortOrder) {
                this.sortOrder = sortOrder;
        }

        public Boolean getActive() {
                return active;
        }

        public void setActive(Boolean active) {
                this.active = active;
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

        public List<Ticket> getTickets() {
                return tickets;
        }

        public void setTickets(List<Ticket> tickets) {
                this.tickets = tickets;
        }

        public List<KbArticle> getArticles() {
                return articles;
        }

        public void setArticles(List<KbArticle> articles) {
                this.articles = articles;
        }
}
