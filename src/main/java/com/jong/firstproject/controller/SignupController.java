package com.jong.firstproject.controller;

import com.jong.firstproject.dto.SignupDto;
import com.jong.firstproject.model.User;
import com.jong.firstproject.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignupController {
    private  final UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(
            @Valid @ModelAttribute("signupDto") SignupDto signupDTO,
            BindingResult bindingResult,
            Model model
            ) {

        System.out.println(model);
        if (bindingResult.hasErrors()) {
            return "signup";
        }

        // 중복 가입 여부 체크
        if (userRepository.findByUsername(signupDTO.getUsername()) != null) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다");

            return "signup";
        }

        User user = User.builder()
                .username(signupDTO.getUsername())
                .password(signupDTO.getPassword())
                .build();

        userRepository.save(user);

        return "redirect:/dto/login?registered";
    }
}