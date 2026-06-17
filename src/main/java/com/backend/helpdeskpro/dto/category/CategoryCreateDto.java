package com.backend.helpdeskpro.dto.category;

public class CategoryCreateDto {

    private String name;
    private String description;
    private Long defaultAssigneeId;
    private Boolean isActive = true;
    private Integer parentId = null;
    private String iconSlug;
    private Integer sortOrder;

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
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

    public Long getDefaultAssigneeId() {
        return defaultAssigneeId;
    }

    public void setDefaultAssigneeId(Long defaultAssigneeId) {
        this.defaultAssigneeId = defaultAssigneeId;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}