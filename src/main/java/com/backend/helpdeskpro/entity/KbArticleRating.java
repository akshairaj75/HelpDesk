package com.backend.helpdeskpro.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "kb_article_ratings", indexes = {
                @Index(name = "idx_rating_article", columnList = "article_id"),
                @Index(name = "idx_rating_user", columnList = "user_id")
}, uniqueConstraints = @UniqueConstraint(name = "uq_rating_user_article", columnNames = { "article_id", "user_id" }))
public class KbArticleRating {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "article_id", nullable = false)
        private KbArticle article;

        @ManyToOne(fetch = FetchType.LAZY, optional = false)
        @JoinColumn(name = "user_id", nullable = false)
        private User user;

        @Column(name = "rating", nullable = false)
        private Integer rating;

        @Column(name = "feedback", columnDefinition = "TEXT")
        private String feedback;

        @CreationTimestamp
        @Column(name = "created_at", nullable = false, updatable = false)
        private LocalDateTime createdAt;

        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        public KbArticle getArticle() {
                return article;
        }

        public void setArticle(KbArticle article) {
                this.article = article;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public Integer getRating() {
                return rating;
        }

        public void setRating(Integer rating) {
                this.rating = rating;
        }

        public String getFeedback() {
                return feedback;
        }

        public void setFeedback(String feedback) {
                this.feedback = feedback;
        }

        public LocalDateTime getCreatedAt() {
                return createdAt;
        }

        public void setCreatedAt(LocalDateTime createdAt) {
                this.createdAt = createdAt;
        }
}
