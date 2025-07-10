package com.jong.firstproject.controller;

import com.jong.firstproject.dto.JpaSignupDto;
import com.jong.firstproject.model.JpaUser;
import com.jong.firstproject.repository.JpaUserRepository;
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
public class JpaSignupController {
    private final JpaUserRepository jpaUserRepository;

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("signupDto", new JpaSignupDto());

        return "jpa-signup";
    }

    @PostMapping("/signup")
    public String signup(
            @Valid @ModelAttribute JpaSignupDto jpaSignupDto,
            BindingResult bindingResult,
            Model model
    ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("signupDto", jpaSignupDto);
            return "jpa-signup";
        }

        if (jpaUserRepository.findByUsername(jpaSignupDto.getUsername()).isPresent()) {
            model.addAttribute("error", "이미 사용중인 아이디입니다");
            return "jpa-signup";
        }

        jpaUserRepository.save(JpaUser.builder()
                .username(jpaSignupDto.getUsername())
                .password(jpaSignupDto.getPassword())
                .build());

        return "redirect:/jpa/login?registered";
    }
}
