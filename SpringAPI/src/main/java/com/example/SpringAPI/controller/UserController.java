package com.example.SpringAPI.controller;

import com.example.SpringAPI.core.dto.NoteResponse;
import com.example.SpringAPI.core.dto.UserRequest;
import com.example.SpringAPI.core.dto.UserResponse;
import com.example.SpringAPI.core.model.User;
import com.example.SpringAPI.core.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return service.getAll()
                .stream()
                .map(UserResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse getOne(@PathVariable Long id) {
        return UserResponse.fromEntity(service.getById(id));
    }


    @PostMapping
    public User create(@RequestBody UserRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody UserRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
