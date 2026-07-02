package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.kbArticles.KbArticleCreateDto;
import com.backend.helpdeskpro.dto.kbArticles.KbArticleResponseDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface KbArticleService {

    List<KbArticleResponseDto> getAllKbArticles();

    KbArticleResponseDto createKbArticle(CustomUserPrincipal authUser, KbArticleCreateDto dto,
            HttpServletRequest request);

    KbArticleResponseDto loadKbArticle(Integer id);


}
