package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
