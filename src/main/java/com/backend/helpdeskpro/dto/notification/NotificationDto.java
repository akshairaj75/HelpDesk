package com.backend.helpdeskpro.dto.notification;

import com.backend.helpdeskpro.entity.Notification;
import com.backend.helpdeskpro.enums.NotificationChannel;
import com.backend.helpdeskpro.enums.NotificationType;

public class NotificationDto {
    private Long notificationId;

    private Long userId;
    private String userName;

    private Long ticketId;
    private String ticketNo;

    private NotificationType type;
    private NotificationChannel channel;

    private String title;
    private String body;
    private String deepLink;

    private Boolean isRead;

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notificationId) {
        this.notificationId = notificationId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getTicketNo() {
        return ticketNo;
    }

    public void setTicketNo(String ticketNo) {
        this.ticketNo = ticketNo;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDeepLink() {
        return deepLink;
    }

    public void setDeepLink(String deepLink) {
        this.deepLink = deepLink;
    }

    public Boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

    public static NotificationDto fromEntity(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setNotificationId(notification.getId());

        if (notification.getUser() != null) {
            dto.setUserId(notification.getUser().getId());
            dto.setUserName(notification.getUser().getFullName());
        }

        if (notification.getTicket() != null) {
            dto.setTicketId(notification.getTicket().getId());
            dto.setTicketNo(notification.getTicket().getTicketNo());
        }

        dto.setType(notification.getType());
        dto.setChannel(notification.getChannel());
        dto.setTitle(notification.getTitle());
        dto.setBody(notification.getBody());
        dto.setDeepLink(notification.getDeepLink());
        dto.setIsRead(notification.getRead());

        return dto;
    }

}
