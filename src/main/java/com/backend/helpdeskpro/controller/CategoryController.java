package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.category.CategoryCreateDto;
import com.backend.helpdeskpro.dto.category.CategoryResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/helpdesk/category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-category")
    public ResponseEntity<CategoryResponseDto> createCategory(
        @AuthenticationPrincipal CustomUserPrincipal authUser,
        @RequestBody CategoryCreateDto dto,
        HttpServletRequest request
    ) {
        CategoryResponseDto resp = categoryService.createCategory(authUser, dto, request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping("get-all-category")
    public ResponseEntity<List<CategoryResponseDto>> getAllCategories() {
        List<CategoryResponseDto> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/add-category/bulk")
    public ResponseEntity<List<CategoryResponseDto>> createCategoryBulk(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody List<CategoryCreateDto> dto,
            HttpServletRequest request) {
        List<CategoryResponseDto> resp = dto.stream()
                .map(d -> categoryService.createCategory(authUser, d, request))
                .toList();
        return ResponseEntity.ok(resp);
    }
    

        
    
    
}
