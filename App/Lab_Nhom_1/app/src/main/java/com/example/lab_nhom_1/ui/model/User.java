package com.example.lab_nhom_1.ui.model;

public class User {
    private Long id;
    private String email;
    private String password;
    private String fullName;

    public User(String email, Long id, String password, String fullName) {
        this.email = email;
        this.id = id;
        this.password = password;
        this.fullName = fullName;
    }
    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
