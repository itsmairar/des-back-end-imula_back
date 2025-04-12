package com.instalab.dtos.responses;

import com.instalab.models.UserModel;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String fullname,
        String enterprise) {

    public static UserResponse parseToUserResponse(UserModel userModel){
        return new UserResponse(
                userModel.getUserId(),
                userModel.getFullname(),
                userModel.getEnterprise()
        );
    }
}
