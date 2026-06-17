package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.KbArticleRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KbArticleRatingRepository extends JpaRepository<KbArticleRating, Long> {

    Optional<KbArticleRating> findByArticleIdAndUserId(Integer articleId, Long userId);
}
