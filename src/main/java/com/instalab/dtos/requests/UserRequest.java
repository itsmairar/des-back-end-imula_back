package com.instalab.dtos.requests;

import com.instalab.entities.UserModel;

public record UserRequest(
        String fullname,
        String email,
        String password,
        String enterprise) {

    public UserModel toUserModel(UserRequest userRequest) {
        return new UserModel(
                userRequest.fullname,
                userRequest.email,
                userRequest.password,
                userRequest.enterprise
        );
    }
}
