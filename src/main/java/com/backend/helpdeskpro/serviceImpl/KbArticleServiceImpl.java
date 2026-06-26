package com.backend.helpdeskpro.serviceImpl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.kbArticles.KbArticleCreateDto;
import com.backend.helpdeskpro.dto.kbArticles.KbArticleResponseDto;
import com.backend.helpdeskpro.entity.Category;
import com.backend.helpdeskpro.entity.KbArticle;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.repository.CategoryRepository;
import com.backend.helpdeskpro.repository.KbArticleRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.KbArticleService;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class KbArticleServiceImpl implements KbArticleService {

    @Autowired
    private KbArticleRepository kbArticleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AuditService auditLogService;

    @Override
    public List<KbArticleResponseDto> getAllKbArticles() {
        List<KbArticle> kbArticles = kbArticleRepository.findAll();
        return kbArticles.stream()
                .map(kbArticle -> {
                    KbArticleResponseDto dto = new KbArticleResponseDto();
                    dto.setTitle(kbArticle.getTitle());
                    dto.setContent(kbArticle.getContent());
                    dto.setAuthor(kbArticle.getAuthor().getFullName());
                    dto.setCategoryId(kbArticle.getCategory().getId());
                    return dto;
                })
                .toList();
    }

    @Override
    public KbArticleResponseDto createKbArticle(CustomUserPrincipal authUser, KbArticleCreateDto dto,
            HttpServletRequest request) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        KbArticle kbArticle = new KbArticle();
        kbArticle.setTitle(dto.getTitle());
        kbArticle.setContent(dto.getContent());
        kbArticle.setStatus(dto.getStatus());
        kbArticle.setAuthor(authUser.getUser());
        kbArticle.setCategory(category);

        kbArticleRepository.save(kbArticle);
        
        // create audit log
        auditLogService.logAction(
                "KB ARTICLE",
                Long.valueOf(kbArticle.getId()),
                authUser.getUser(),
                AuditAction.CREATED,
                Map.of(
                        "title", kbArticle.getTitle(),
                        "status", kbArticle.getStatus(),
                        "author", kbArticle.getAuthor().getFullName(),
                        "categoryId", kbArticle.getCategory().getId()),
                request);

        return KbArticleResponseDto.fromEntity(kbArticle);

    }

}
