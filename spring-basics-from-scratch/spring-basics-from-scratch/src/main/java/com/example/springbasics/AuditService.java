package com.example.springbasics;

import org.springframework.stereotype.Service;

@Service
public class AuditService {
    public void send(String message){
        System.out.println("AUDIT: " + message);
    }
}
