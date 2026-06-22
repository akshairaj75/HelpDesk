package com.backend.helpdeskpro.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.helpdeskpro.dto.notification.NotificationDto;
import com.backend.helpdeskpro.security.CustomUserPrincipal;
import com.backend.helpdeskpro.service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/helpdesk/notification")
public class NotificationController {

    @Autowired
    NotificationService notificatService;

    @GetMapping("/my")
    public ResponseEntity<List<NotificationDto>> getMyNotifications(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        List<NotificationDto> notifications = notificatService.getNotificationsForUser(authUser);
        return ResponseEntity.ok(notifications);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<Void> markAsRead(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
            @PathVariable Long notificationId,
        HttpServletRequest request) {
        notificatService.markAsRead(notificationId,authUser,request);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/read-all")
    public ResponseEntity<Void> markAllAsRead(
            @AuthenticationPrincipal CustomUserPrincipal authUser,
        HttpServletRequest request) {
        notificatService.markAllAsRead(authUser,request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unread-count")
    public ResponseEntity<Long> getUnreadCount(
            @AuthenticationPrincipal CustomUserPrincipal authUser) {
        long count = notificatService.getUnreadCount(authUser);
        return ResponseEntity.ok(count);
    }

}
