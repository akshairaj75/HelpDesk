package com.backend.helpdeskpro.dto.kbArticles;

import com.backend.helpdeskpro.entity.KbArticle;

public class KbArticleResponseDto {

    private Integer articleId;

    private String title;
    private String content;
    private Integer categoryId;
    private String tags;
    private boolean published;
    private String author;

    public Integer getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    public String getTags() {
        return tags;
    }
    public void setTags(String tags) {
        this.tags = tags;
    }
    public boolean isPublished() {
        return published;
    }
    public void setPublished(boolean published) {
        this.published = published;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getArticleId() {
        return articleId;
    }
    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }
    public static KbArticleResponseDto fromEntity(KbArticle kbArticle) {
        KbArticleResponseDto response = new KbArticleResponseDto();
        response.setArticleId(kbArticle.getId());
        response.setTitle(kbArticle.getTitle());
        response.setContent(kbArticle.getContent());
        response.setCategoryId(kbArticle.getCategory().getId());
        response.setAuthor(kbArticle.getAuthor().getFullName());
        return response;
    }

}
