package com.jong.firstproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")  // ✅ 올바른 어노테이션
public class RestHelloController {
    @GetMapping
    public String sayHello() {
        return "Hello, Actuator!";
    }
}