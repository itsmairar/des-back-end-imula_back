package com.instalab.controllers;

import com.instalab.dtos.requests.UserRequest;
import com.instalab.dtos.responses.UserResponse;
import com.instalab.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

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

    //Endpoint para listar usuarios cadastrados
    //Perfil: Admin
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userService.getAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    //Endpoint para listar usuário cadastrado
    //Perfil: Admin
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUser(@PathVariable UUID userId) {
        UserResponse user = userService.getUser(userId);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    //Endpoint para atualizar usuário
    //Perfil: Admin
    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateUser(@PathVariable UUID userId, @RequestBody UserRequest userRequest) {
        userService.updateUser(userRequest, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //Endpoint para deletar usuário
    //Perfil: Admin
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }





}
