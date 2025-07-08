package com.jong.firstproject.controller;

import com.jong.firstproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/dto")
public class LoginController {
    private  final UserRepository userRepository;

    @GetMapping({"", "/", "/login"})
    public String showLogin() {
        return "login";
    }
}
