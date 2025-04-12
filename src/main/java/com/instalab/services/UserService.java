package com.instalab.services;

import com.instalab.dtos.requests.UserRequest;
import com.instalab.dtos.responses.UserResponse;
import com.instalab.models.UserModel;
import com.instalab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserResponse createUser(UserRequest userRequest) {
        UserModel newUser = new UserModel(
                userRequest.fullname(),
                userRequest.email(),
                userRequest.password(),
                userRequest.enterprise());
        userRepository.save(newUser);
        return UserResponse.parseToUserResponse(newUser);
    }

}
