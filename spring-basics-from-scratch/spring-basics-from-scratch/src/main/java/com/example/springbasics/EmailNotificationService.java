package com.example.springbasics;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class EmailNotificationService implements NotificationService{
    @Value("${notification.prefix}")
    private String prefix;

    public void send(String message){
        System.out.println("\n" + prefix + " Sending EMAIL notification: " + message);
    }
}
