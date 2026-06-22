package com.backend.helpdeskpro.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.category.CategoryCreateDto;
import com.backend.helpdeskpro.dto.category.CategoryResponseDto;
import com.backend.helpdeskpro.entity.Category;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.repository.CategoryRepository;
import com.backend.helpdeskpro.repository.UserRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.CategoryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditService auditLogService;

    @Transactional
    @Override
    public CategoryResponseDto createCategory(CustomUserPrincipal authUser, CategoryCreateDto dto,HttpServletRequest request) {
        Category category = new Category();
        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setIconSlug(dto.getIconSlug());
        category.setSortOrder(dto.getSortOrder() != null ? dto.getSortOrder() : 0);

        if (dto.getParentId() != null) {
            Category parent = categoryRepository.findById(dto.getParentId())
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParent(parent);
        }

        if (dto.getDefaultAssigneeId() != null) {
            User assignee = userRepository.findById(dto.getDefaultAssigneeId())
                    .orElseThrow(() -> new RuntimeException("Default assignee not found"));
            category.setDefaultAssignee(assignee);
        }

        Category savedCategory = categoryRepository.save(category);

        auditLogService.logAction(
                "CATEGORY",
                Long.valueOf(savedCategory.getId()),
                authUser.getUser(),
                AuditAction.CREATED,
                Map.of(
                        "name", savedCategory.getName(),
                        "parentId", savedCategory.getParent() != null
                                ? savedCategory.getParent().getId()
                                : 0),
                request);

        return CategoryResponseDto.fromEntity(category);

    }

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(CategoryResponseDto::fromEntity)
                .toList();

    }

}
