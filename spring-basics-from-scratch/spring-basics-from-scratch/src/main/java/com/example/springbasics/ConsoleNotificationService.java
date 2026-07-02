package com.example.springbasics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ConsoleNotificationService implements NotificationService{
    @Value("${notification.prefix}")
    private String prefix;

    @Override
    public void send(String message) {
        System.out.println(prefix + " Sending CONSOLE Notification: " + message);
    }
}
