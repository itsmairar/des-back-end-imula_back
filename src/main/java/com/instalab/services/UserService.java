package com.instalab.services;

import com.instalab.dtos.requests.UserRequest;
import com.instalab.dtos.responses.UserResponse;
import com.instalab.entities.Role;
import com.instalab.entities.UserModel;
import com.instalab.enums.RoleEnum;
import com.instalab.repositories.RoleRepository;
import com.instalab.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.roleRepository  = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UserResponse createUser(UserRequest userRequest) {
        UserModel user = userRequest.toUserModel();
        user.setPassword(passwordEncoder.encode(userRequest.password()));

        // mapeia roles do JSON
        Set<String> requested = Optional.ofNullable(userRequest.roles()).orElse(Set.of());
        Set<Role> roles = requested.stream()
            .map(RoleEnum::valueOf)
            .map(enumRole -> roleRepository.findByName(enumRole)
                .orElseThrow(() -> new RuntimeException("Role não encontrada: " + enumRole)))
            .collect(Collectors.toSet());

        if (roles.isEmpty()) {
            Role defaultRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROLE_USER não cadastrada"));
            roles.add(defaultRole);
        }
        user.setRoles(roles);

        userRepository.save(user);
        return UserResponse.parseToUserResponse(user);
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
        UserModel user = userRepository.findById(userId)
            .orElseThrow(() -> new NoSuchElementException("User not found"));

        user.setFullname(userRequest.fullname());
        user.setEmail(userRequest.email());

        if(userRequest.password() != ""){
            user.setPassword(passwordEncoder.encode(userRequest.password()));
        }
        user.setEnterprise(userRequest.enterprise());

        // atualiza roles também
        Set<String> requested = Optional.ofNullable(userRequest.roles()).orElse(Set.of());
        Set<Role> roles = requested.stream()
            .map(RoleEnum::valueOf)
            .map(enumRole -> roleRepository.findByName(enumRole)
                .orElseThrow(() -> new RuntimeException("Role não encontrada: " + enumRole)))
            .collect(Collectors.toSet());
        if (roles.isEmpty()) {
            Role defaultRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("ROLE_USER não cadastrada"));
            roles.add(defaultRole);
        }
        user.setRoles(roles);

        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }
}
