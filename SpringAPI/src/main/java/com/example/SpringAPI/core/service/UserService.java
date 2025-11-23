package com.example.SpringAPI.core.service;

import com.example.SpringAPI.core.dto.UserRequest;
import com.example.SpringAPI.core.model.User;
import com.example.SpringAPI.core.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> getAll() {
        return repo.findAll();
    }

    public User getById(Long id) {
        return repo.findById(id).orElseThrow();
    }

    public User create(UserRequest req) {
        User u = new User();
        u.setEmail(req.email);
        u.setPassword(req.password);
        u.setAdminRights(req.adminRights);
        return repo.save(u);
    }

    public User update(Long id, UserRequest req) {
        User u = getById(id);
        u.setEmail(req.email);
        u.setPassword(req.password);
        u.setAdminRights(req.adminRights);
        return repo.save(u);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
