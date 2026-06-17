package com.backend.helpdeskpro.repository;

import com.backend.helpdeskpro.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findByUserIdAndReadFalseOrderBySentAtDesc(Long userId);
}
