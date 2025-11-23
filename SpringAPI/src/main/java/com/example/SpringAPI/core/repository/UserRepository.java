package com.example.SpringAPI.core.repository;

import com.example.SpringAPI.core.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {}
