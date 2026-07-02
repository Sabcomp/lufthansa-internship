package com.example.springbasics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements CommandLineRunner {
    @Value("#{systemProperties['user.name']}")
    private String user;

    @Value("${Path}")
    private String path;

    private final NotificationManager notificationManager;
    private final ApplicationContext applicationContext;

    @Autowired
    public AppRunner(NotificationManager notificationManager, ApplicationContext applicationContext){
        this.notificationManager = notificationManager;
        this.applicationContext = applicationContext;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\nSystem started by: " + user);
        System.out.println("Value of PATH variable: " + path);

        notificationManager.notifyUser("Welcome to Spring!", "Notification was sent");

        EmailNotificationService service1 = applicationContext.getBean(EmailNotificationService.class);
        EmailNotificationService service2 = applicationContext.getBean(EmailNotificationService.class);
        System.out.println("Bean instances are the same: " + (service1 == service2));
    }
}
