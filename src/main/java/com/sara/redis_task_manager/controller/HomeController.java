package com.sara.redis_task_manager.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String hello() {
        return "Backend is running ✅";
    }
}
