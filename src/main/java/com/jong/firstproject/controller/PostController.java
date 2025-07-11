package com.jong.firstproject.controller;

import com.jong.firstproject.dto.CommentDto;
import com.jong.firstproject.dto.PostDto;
import com.jong.firstproject.model.Comment;
import com.jong.firstproject.model.JpaUser;
import com.jong.firstproject.model.Post;
import com.jong.firstproject.repository.CommentRepository;
import com.jong.firstproject.repository.PostRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/jpa/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    private JpaUser currentUser(HttpSession httpSession) {
        return (JpaUser) httpSession.getAttribute("user");
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("posts", postRepository.findAll());

        return "post-list";
    }

    @GetMapping("/add")
    public String addForm(Model model, HttpSession httpSession) {
        if (currentUser(httpSession) == null) return "redirect:/login";

        model.addAttribute("postDto", new PostDto());

        return "post-form";
    }

    @PostMapping("/add")
    public String add(
            @Valid @ModelAttribute PostDto postDto,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {
        if (bindingResult.hasErrors()) return "post-form";

        JpaUser jpaUser = currentUser(httpSession);
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .author(jpaUser)
                .createdAt(LocalDateTime.now())
                .build();

        postRepository.save(post);

        return "redirect:/jpa/posts";
    }

    @GetMapping("/{id}")
    public String detail(
            @PathVariable Integer id,
            Model model,
            HttpSession httpSession
    ) {
        Post post = postRepository.findById(id).orElseThrow();

        model.addAttribute("commentDto", new CommentDto());
        model.addAttribute("post", post);

        return "post-detail";
    }

    @PostMapping("/{postId}/comments")
    public String addComment(
            @PathVariable Integer postId,
            @Valid @ModelAttribute CommentDto commentDto,
            BindingResult bindingResult,
            HttpSession httpSession,
            Model model
    ) {
        Post post = postRepository.findById(postId).orElseThrow();

        if (bindingResult.hasErrors()) {
            model.addAttribute("post", post);

            return "post-detail";
        }

        JpaUser jpaUser = currentUser(httpSession);
        Comment comment = Comment.builder()
                .post(post)
                .author(jpaUser)
                .text(commentDto.getText())
                .createdAt(LocalDateTime.now())
                .build();
        commentRepository.save(comment);

        return "redirect:/jpa/posts/" + postId;
    }
}
