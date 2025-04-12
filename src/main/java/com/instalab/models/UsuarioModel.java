package com.instalab.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="usuario_tb")
public class UsuarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    private String fullname;
    private String email;
    private String password;
    private String enterprise;

    public UsuarioModel() {}
    public UsuarioModel(UUID userId, String fullname, String email, String password, String enterprise) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.enterprise = enterprise;
    }



}
