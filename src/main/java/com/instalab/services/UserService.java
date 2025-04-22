package com.instalab.services;

import com.instalab.dtos.requests.UserRequest;
import com.instalab.dtos.responses.UserResponse;
import com.instalab.entities.UserModel;
import com.instalab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        String hashedPassword = passwordEncoder.encode(userRequest.password());

        UserModel newUser = new UserModel(
            userRequest.fullname(),
            userRequest.email(),
            hashedPassword,
            userRequest.enterprise()
        );

        userRepository.save(newUser);
        return UserResponse.parseToUserResponse(newUser);
    }

    public UserResponse getUser(UUID userId) {
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found"));
        return UserResponse.parseToUserResponse(user);
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
            .map(UserResponse::parseToUserResponse)
            .collect(Collectors.toList());
    }

    @Transactional
    public void updateUser(UserRequest userRequest, UUID userId) {
        UserModel userRegistered = userRepository.findByUserId(userId);
        if (userRegistered == null) {
            throw new NoSuchElementException("User not found");
        }

        userRegistered.setFullname(userRequest.fullname());
        userRegistered.setEmail(userRequest.email());
        userRegistered.setPassword(passwordEncoder.encode(userRequest.password()));
        userRegistered.setEnterprise(userRequest.enterprise());

        userRepository.save(userRegistered);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
