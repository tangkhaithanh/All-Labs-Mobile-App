package org.example.lab_nhom_1.entity;

import jakarta.persistence.*;
@Table(name = "Users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "email", nullable = false, columnDefinition = "nvarchar(255)")
    private String email;

    @Column(name = "password", nullable = false, columnDefinition = "nvarchar(255)")
    private String password;

    @Column(name = "fullName", nullable = false, columnDefinition = "nvarchar(255)")
    private String fullName;
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
