package com.instalab.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="usuario_tb")
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID userId;
    private String fullname;
    private String email;
    private String password;
    private String enterprise;

    public UserModel() {}
    public UserModel(UUID userId, String fullname, String email, String password, String enterprise) {
        this.userId = userId;
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.enterprise = enterprise;
    }

    public UserModel(String fullname, String email, String password, String enterprise) {
        this.fullname = fullname;
        this.email = email;
        this.password = password;
        this.enterprise = enterprise;
    }

    public UUID getUserId() {
        return userId;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(String enterprise) {
        this.enterprise = enterprise;
    }
}
