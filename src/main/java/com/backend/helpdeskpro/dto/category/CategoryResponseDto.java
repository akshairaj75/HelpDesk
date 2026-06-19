package com.backend.helpdeskpro.dto.category;

public class CategoryResponseDto {

    private Integer categoryId;
    private CategoryResponseDto parentCategory;
    private String name;
    private String description;
    private Boolean isActive;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


    public CategoryResponseDto getParentCategory() {
        return parentCategory;
    }
            
    public void setParentCategory(CategoryResponseDto parentCategory) {
        this.parentCategory = parentCategory;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public static CategoryResponseDto fromEntity(com.backend.helpdeskpro.entity.Category category) {
        CategoryResponseDto dto = new CategoryResponseDto();
        dto.setCategoryId(category.getId());

        if (category.getParent() != null) {
            dto.setParentCategory(CategoryResponseDto.fromEntity(category.getParent()));
        }
        
        dto.setName(category.getName());
        dto.setDescription(category.getDescription());
        dto.setIsActive(category.getActive());
        return dto;
    }
    
}