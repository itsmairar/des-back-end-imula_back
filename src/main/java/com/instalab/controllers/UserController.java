package com.instalab.controllers;

import com.instalab.dtos.requests.UserRequest;
import com.instalab.dtos.responses.UserResponse;
import com.instalab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Endpoint para cadastrar novo usuário
    //Perfil: Admin
    @PostMapping("/new")
    public ResponseEntity<UserResponse> newUser(@RequestBody UserRequest userRequest) {
        UserResponse user = userService.createUser(userRequest);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.userId()).toUri();
        return ResponseEntity.created(uri).build();
    }






}
