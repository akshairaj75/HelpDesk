package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.category.CategoryCreateDto;
import com.backend.helpdeskpro.dto.category.CategoryResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface CategoryService {

    CategoryResponseDto createCategory(CustomUserPrincipal authUser, CategoryCreateDto dto, HttpServletRequest request);

    List<CategoryResponseDto> getAllCategories();

}
