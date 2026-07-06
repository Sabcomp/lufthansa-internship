package com.lhind.demo;

import org.springframework.web.bind.annotation.*;

@RestController
public class GreetingController {
    private final GreetingService greetingService;

    public GreetingController(GreetingService greetingService){
        this.greetingService = greetingService;
    }

    @GetMapping("/greet")
    public String greetByName(@RequestParam(required = false) String name){
        if (name == null)
            return greetingService.greet("Team");
        return greetingService.greet(name);
    }
}
