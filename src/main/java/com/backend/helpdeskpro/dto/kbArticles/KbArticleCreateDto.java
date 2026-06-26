package com.backend.helpdeskpro.dto.kbArticles;

import com.backend.helpdeskpro.entity.KbArticle;
import com.backend.helpdeskpro.enums.KbArticleStatus;

public class KbArticleCreateDto {
    private Integer categoryId;
    private Long authorId;

    private String title;
    private String content;
    private KbArticleStatus status;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
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

    public static KbArticleResponseDto fromEntity(KbArticle kbArticle) {
        KbArticleResponseDto response = new KbArticleResponseDto();
        response.setCategoryId(kbArticle.getCategory().getId());
        response.setAuthor(kbArticle.getAuthor().getFullName());
        response.setContent(kbArticle.getContent());
        response.setTitle(kbArticle.getTitle());
        return response;
    }

}
