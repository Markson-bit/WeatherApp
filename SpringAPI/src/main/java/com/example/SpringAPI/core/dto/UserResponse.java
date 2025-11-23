package com.example.SpringAPI.core.dto;

import com.example.SpringAPI.core.model.User;

public class UserResponse {
    public Long id;
    public String email;
    public boolean adminRights;

    public static UserResponse fromEntity(User u) {
        UserResponse r = new UserResponse();
        r.id = u.getId();
        r.email = u.getEmail();
        r.adminRights = u.isAdminRights();
        return r;
    }
}
