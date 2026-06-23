package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCase(String email);


    List<User> findByRole(UserRole role);

    List<User> findAllByOrderByCreatedAtDesc();
}
