package com.example.SpringAPI.core.dto.auth;

public class AuthResponse {

    private String token;
    private String email;
    private boolean admin;

    public AuthResponse() {
    }

    public AuthResponse(String token, String email, boolean admin) {
        this.token = token;
        this.email = email;
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
