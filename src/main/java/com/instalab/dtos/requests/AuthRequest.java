package com.instalab.dtos.requests;

import jakarta.validation.constraints.NotBlank;

public record AuthRequest(@NotBlank
                          String username,
                          @NotBlank
                          String password) {


//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
}
