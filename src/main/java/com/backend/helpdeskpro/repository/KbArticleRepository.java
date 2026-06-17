package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.KbArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KbArticleRepository extends JpaRepository<KbArticle, Integer> {
}
