package com.backend.helpdeskpro.entity;

import com.backend.helpdeskpro.enums.KbArticleStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kb_articles", indexes = {
                @Index(name = "idx_kb_category", columnList = "category_id"),
                @Index(name = "idx_kb_author", columnList = "author_id"),
                @Index(name = "idx_kb_status", columnList = "status"),
                @Index(name = "idx_kb_views", columnList = "view_count")
})
public class KbArticle {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Integer id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        private Category category;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "author_id", nullable = false)
        private User author;

        @Column(name = "title", nullable = false, length = 255)
        private String title;

        @Column(name = "content", nullable = false, columnDefinition = "LONGTEXT")
        private String content;

        @Enumerated(EnumType.STRING)
        @Column(name = "status", nullable = false, length = 20)
        private KbArticleStatus status = KbArticleStatus.DRAFT;

        @Column(name = "view_count", nullable = false)
        private Integer viewCount = 0;

        @Column(name = "avg_rating", precision = 3, scale = 2)
        private BigDecimal avgRating;

        @Column(name = "published_at")
        private LocalDateTime publishedAt;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = false)
        private LocalDateTime updatedAt;

        @OneToMany(mappedBy = "article")
        private List<KbArticleRating> ratings = new ArrayList<>();

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public Category getCategory() {
                return category;
        }

        public void setCategory(Category category) {
                this.category = category;
        }

        public User getAuthor() {
                return author;
        }

        public void setAuthor(User author) {
                this.author = author;
        }

        public String getTitle() {
                return title;
        }

        public void setTitle(String title) {
                this.title = title;
        }

        public String getContent() {
                return content;
        }

        public void setContent(String content) {
                this.content = content;
        }

        public KbArticleStatus getStatus() {
                return status;
        }

        public void setStatus(KbArticleStatus status) {
                this.status = status;
        }

        public Integer getViewCount() {
                return viewCount;
        }

        public void setViewCount(Integer viewCount) {
                this.viewCount = viewCount;
        }

        public BigDecimal getAvgRating() {
                return avgRating;
        }

        public void setAvgRating(BigDecimal avgRating) {
                this.avgRating = avgRating;
        }

        public LocalDateTime getPublishedAt() {
                return publishedAt;
        }

        public void setPublishedAt(LocalDateTime publishedAt) {
                this.publishedAt = publishedAt;
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

        public List<KbArticleRating> getRatings() {
                return ratings;
        }

        public void setRatings(List<KbArticleRating> ratings) {
                this.ratings = ratings;
        }
}
