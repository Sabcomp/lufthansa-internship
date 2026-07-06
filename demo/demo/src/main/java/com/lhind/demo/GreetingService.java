package com.lhind.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
//    @Value("${app.greeting}")
//    private String greeting;

    private final AppProperties appProperties;

    public GreetingService(AppProperties appProperties){
        this.appProperties = appProperties;
    }

    public String greet(String name){
        return appProperties.greeting() + " " + name;
    }
}
