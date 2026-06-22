package com.backend.helpdeskpro.service;

import java.util.List;

import com.backend.helpdeskpro.dto.notification.NotificationDto;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.NotificationType;
import com.backend.helpdeskpro.security.CustomUserPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public interface NotificationService {

    List<NotificationDto> getNotificationsForUser(CustomUserPrincipal authUser);

    void markAsRead(Long notificationId, CustomUserPrincipal authUser, HttpServletRequest request);

    void markAllAsRead(CustomUserPrincipal authUser, HttpServletRequest request);

    void createNotification(
            User receiver,
            Ticket ticket,
            NotificationType type,
            String title,
            String body,
            String deepLink);

    long getUnreadCount(CustomUserPrincipal authUser);
}
