package com.jong.firstproject.controller;

import com.jong.firstproject.dto.TodoDto;
import com.jong.firstproject.model.DtoTodo;
import com.jong.firstproject.model.User;
import com.jong.firstproject.repository.DtoTodoRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/dto/todos")
@RequiredArgsConstructor
public class DtoTodoController {
    private final DtoTodoRepository dtoTodoRepository;

    private User getCurrentUser(HttpSession session) {
        return (User) session.getAttribute("user");
    }

    @GetMapping
    public String list(HttpSession httpSession, Model model) {
        User user = getCurrentUser(httpSession);

        if(user == null) {
            return "redirect:/dto/login";
        }

        List<DtoTodo> list = dtoTodoRepository.findAllByUserId(user.getId());
        model.addAttribute("todos", list);

        return "dto-todo-list";
    }

    @GetMapping("/add")
    public String addForm(HttpSession httpSession, Model model) {
        if (getCurrentUser(httpSession) == null) return "redirect:/dto/login";

        model.addAttribute("todoDto", new TodoDto());

        return "dto-todo-form";
    }

    @PostMapping("/add")
    public String add (
            @Valid @ModelAttribute DtoTodo dtoTodo,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {
        if (bindingResult.hasErrors()) return "todo-form";

        User user = getCurrentUser(httpSession);

        DtoTodo todo = DtoTodo.builder()
                .userId(user.getId())
                .title(dtoTodo.getTitle())
                .completed(dtoTodo.isCompleted())
                .build();

        dtoTodoRepository.save(todo);

        return "redirect:/dto/todos";
    }

    @PostMapping("/delete/{id}")
    public String delete(
            @PathVariable int id,
            HttpSession httpSession
    ) {
        User user = getCurrentUser(httpSession);
        dtoTodoRepository.deleteByIdAndUserId(id, user.getId());

        return "redirect:/dto/todos";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable int id,
            Model model,
            HttpSession httpSession
    ) {
        User user = getCurrentUser(httpSession);

        if (user == null) return "redirect:/login";

        DtoTodo dtoTodo = dtoTodoRepository.findByIdAndUserId(id, user.getId());
        TodoDto dto = new TodoDto();
        dto.setId(dtoTodo.getId());
        dto.setTitle(dtoTodo.getTitle());
        dto.setCompleted(dtoTodo.isCompleted());

        model.addAttribute("todoDto", dto);

        return "dto-todo-form";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid @ModelAttribute TodoDto todoDto,
            BindingResult bindingResult,
            HttpSession httpSession
    ) {

        if(bindingResult.hasErrors()) return "dto-todo-form";

        User user = getCurrentUser(httpSession);
        DtoTodo dtoTodo = DtoTodo.builder()
                .id(todoDto.getId())
                .title(todoDto.getTitle())
                .completed(todoDto.isCompleted())
                .userId(user.getId()).build();

        dtoTodoRepository.update(dtoTodo);

        return "redirect:/dto/todos";
    }
}
