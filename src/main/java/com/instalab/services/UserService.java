package com.instalab.services;

import com.instalab.dtos.requests.UserRequest;
import com.instalab.dtos.responses.SoftwareResponse;
import com.instalab.dtos.responses.UserResponse;
import com.instalab.models.UserModel;
import com.instalab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        UserModel newUser = new UserModel(
                userRequest.fullname(),
                userRequest.email(),
                userRequest.password(),
                userRequest.enterprise());
        userRepository.save(newUser);
        return UserResponse.parseToUserResponse(newUser);
    }

    public UserResponse getUser(UUID userId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        return UserResponse.parseToUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        List<UserModel> users = userRepository.findAll();
        return users.stream()
                .map(UserResponse::parseToUserResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserRequest userRequest, UUID userId) {
        UserModel userRegistred = userRepository.findByUserId(userId);
        buildChanges(userRegistred, userRequest);
        userRepository.save(userRegistred);
    }

    private static UserModel buildChanges(UserModel userRegistred, UserRequest userRequest){
        userRegistred.setFullname(userRequest.fullname());
        userRegistred.setEmail(userRequest.email());
        userRegistred.setPassword(userRequest.password());
        userRegistred.setEnterprise(userRequest.enterprise());
        return userRegistred;
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }



}
