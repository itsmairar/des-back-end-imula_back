package com.instalab.dtos.requests;

import com.instalab.entities.UserModel;
import java.util.Set;

public record UserRequest(
    String fullname,
    String email,
    String password,
    String enterprise,
    Set<String> roles
) {
    public UserModel toUserModel() {
        return new UserModel(
            this.fullname,
            this.email,
            this.password,
            this.enterprise
        );
    }
}
