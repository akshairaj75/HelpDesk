package com.backend.helpdeskpro.serviceImpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.helpdeskpro.dto.notification.NotificationDto;
import com.backend.helpdeskpro.entity.Notification;
import com.backend.helpdeskpro.entity.Ticket;
import com.backend.helpdeskpro.entity.User;
import com.backend.helpdeskpro.enums.AuditAction;
import com.backend.helpdeskpro.enums.NotificationChannel;
import com.backend.helpdeskpro.enums.NotificationType;
import com.backend.helpdeskpro.repository.NotificationRepository;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.AuditService;
import com.backend.helpdeskpro.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    AuditService auditLogService;

    @Override
    public List<NotificationDto> getNotificationsForUser(CustomUserPrincipal authUser) {
        return notificationRepository.findByUserIdOrderBySentAtDesc(authUser.getUserId())
                .stream()
                .map(NotificationDto::fromEntity)
                .toList();

    }

    @Override
    public void markAsRead(Long notificationId, CustomUserPrincipal authUser, HttpServletRequest request) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        notification.setRead(true);
        notificationRepository.save(notification);
        auditLogService.logAction(
                "NOTIFICATION",
                notification.getId(),
                authUser.getUser(),
                AuditAction.NOTIFICATION_READ,
                Map.of(
                        "title", notification.getTitle(),
                        "type", notification.getType().name()),
                request);

    }

    @Override
    public void markAllAsRead(CustomUserPrincipal authUser, HttpServletRequest request) {
        List<Notification> notifications = notificationRepository
                .findByUserIdAndReadFalseOrderBySentAtDesc(authUser.getUserId());
        for (Notification notification : notifications) {
            notification.setRead(true);
            Notification savedNotification = notificationRepository.save(notification);

            auditLogService.logAction(
                    "NOTIFICATION",
                    savedNotification.getId(),
                    savedNotification.getUser(),
                    AuditAction.NOTIFICATION_READ,
                    Map.of(
                            "title", savedNotification.getTitle(),
                            "type", savedNotification.getType().name()),
                    request);
        }

    }

    @Override
    @Transactional
    public void createNotification(User receiver, Ticket ticket, NotificationType type, String title, String body,
            String deepLink) {
        if (receiver == null) {
            return;
        }
        Notification notification = new Notification();
        notification.setUser(receiver);
        notification.setTicket(ticket);
        notification.setType(type);

        notification.setChannel(NotificationChannel.IN_APP);

        notification.setTitle(title);
        notification.setBody(body);
        notification.setDeepLink(deepLink);

        notification.setRead(false);
        notification.setSentAt(LocalDateTime.now());

        notificationRepository.save(notification);

    }

    @Override
    public long getUnreadCount(CustomUserPrincipal authUser) {
        return notificationRepository.countByUserIdAndReadFalse(authUser.getUserId());
    }
}
