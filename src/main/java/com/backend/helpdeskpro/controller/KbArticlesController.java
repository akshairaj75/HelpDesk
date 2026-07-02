package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.kbArticles.KbArticleCreateDto;
import com.backend.helpdeskpro.dto.kbArticles.KbArticleResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.KbArticleService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/helpdesk/kb")
public class KbArticlesController {

    @Autowired
    KbArticleService kbArticleService;

    @GetMapping("/articles")
    public ResponseEntity<List<KbArticleResponseDto>> getAllKbArticles() {
        List<KbArticleResponseDto> articles = kbArticleService.getAllKbArticles();
        return ResponseEntity.ok(articles);
    }

    @PostMapping("/create")
    public ResponseEntity<KbArticleResponseDto> createKbArticle(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody KbArticleCreateDto dto,
            HttpServletRequest request) {
        KbArticleResponseDto article = kbArticleService.createKbArticle(authUser, dto, request);
        return ResponseEntity.ok(article);
    }

    @PostMapping("/create/bulk")
    public ResponseEntity<List<KbArticleResponseDto>> createBulkKbArticles(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @RequestBody List<KbArticleCreateDto> dtos,
            HttpServletRequest request) {
        List<KbArticleResponseDto> articles = dtos.stream()
                .map(dto -> kbArticleService.createKbArticle(authUser, dto, request))
                .toList();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/load-kb/{id}")
    public ResponseEntity<KbArticleResponseDto> loadKbArticle(@PathVariable Integer id) {
        KbArticleResponseDto article = kbArticleService.loadKbArticle(id);
        return ResponseEntity.ok(article);
    }
}
