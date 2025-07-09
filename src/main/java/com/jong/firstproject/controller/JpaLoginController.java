package com.jong.firstproject.controller;

import com.jong.firstproject.dto.JpaLoginDto;
import com.jong.firstproject.model.JpaUser;
import com.jong.firstproject.repository.JpaUserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/jpa")
public class JpaLoginController {
    private final JpaUserRepository jpaUserRepository;

    @GetMapping({"", "/login"})
    public String loginForm(Model model) {
        model.addAttribute("loginDto", new JpaLoginDto());

        return "jpa-login";
    }

    @PostMapping("/login")
    public String login(
            @Valid @ModelAttribute JpaLoginDto jpaLoginDto,
            BindingResult bindingResult,
            HttpSession httpSession,
            Model model
    ) {
        if (bindingResult.hasErrors()) return "jpa-login";

        JpaUser jpaUser = jpaUserRepository.findByUsername(jpaLoginDto.getUsername()).orElse(null);

        if (jpaUser == null || !jpaUser.getPassword().equals(jpaLoginDto.getPassword())) {
            model.addAttribute("error", "아이디/비밀번호가 올바르지 않습니다");
            return "jpa-login";
        }

        httpSession.setAttribute("user", jpaUser);

        return "redirect:/jpa/posts";
    }
}
