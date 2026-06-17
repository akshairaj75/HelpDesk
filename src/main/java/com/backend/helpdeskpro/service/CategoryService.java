package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.category.CategoryCreateDto;
import com.backend.helpdeskpro.dto.category.CategoryResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

public interface CategoryService {

    CategoryResponseDto createCategory(CustomUserPrincipal authUser, CategoryCreateDto dto);

    List<CategoryResponseDto> getAllCategories();

}
