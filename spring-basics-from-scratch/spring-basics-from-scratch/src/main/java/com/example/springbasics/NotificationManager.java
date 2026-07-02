package com.example.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {
    private final NotificationService notificationService;
    private AuditService auditService;

    @Autowired
    public NotificationManager(@Qualifier("emailNotificationService") NotificationService notificationService){
        this.notificationService = notificationService;
    }

    @Autowired
    public void setAuditService(AuditService auditService){
        this.auditService = auditService;
    }

    public void notifyUser(String message, String auditMessage){
        notificationService.send(message);
        auditService.send(auditMessage);
    }
}
