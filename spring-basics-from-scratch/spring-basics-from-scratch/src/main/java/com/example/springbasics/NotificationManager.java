package com.example.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotificationManager {
    private final NotificationService notificationService;

    @Autowired
    public NotificationManager(NotificationService notificationService){
        this.notificationService = notificationService;
    }

    public void notifyUser(String message){
        notificationService.send(message);
    }
}
