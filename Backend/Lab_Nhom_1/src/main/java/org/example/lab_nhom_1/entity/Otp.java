package org.example.lab_nhom_1.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Table(name="otps")
@Entity
public class Otp
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, columnDefinition = "nvarchar(255)")
    private String email;

    @Column(name = "otp", nullable = false, columnDefinition = "nvarchar(255)")
    private String otp;

    @Column(name = "expiry_time", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime expiryTime;

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean used;

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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }
}
