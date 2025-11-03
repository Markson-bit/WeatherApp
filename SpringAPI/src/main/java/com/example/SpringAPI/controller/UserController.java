package com.example.SpringAPI.controller;

import com.example.SpringAPI.core.model.User;
import com.example.SpringAPI.core.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/health")
    public String health() {
        return "OK";
    }

    @GetMapping("/users")
    public List<User> users() {
        return userService.getAll();
    }
}
