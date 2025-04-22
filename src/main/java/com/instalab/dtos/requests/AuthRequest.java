package com.instalab.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public class AuthRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
