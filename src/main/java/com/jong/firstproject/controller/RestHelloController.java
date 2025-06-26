package com.jong.firstproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class RestHelloController {
    @GetMapping
    public String sayHello() {
        return "Hello, Actuator!";
    }
}