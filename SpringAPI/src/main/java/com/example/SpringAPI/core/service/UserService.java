package com.example.SpringAPI.core.service;

import com.example.SpringAPI.core.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    public List<User> getAll() {
        return List.of(
                new User(1L, "Ala"),
                new User(2L, "Jan")
        );
    }
}
