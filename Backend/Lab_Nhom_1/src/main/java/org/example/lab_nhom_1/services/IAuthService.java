package org.example.lab_nhom_1.services;

import org.example.lab_nhom_1.dto.LoginRequest;
import org.example.lab_nhom_1.dto.RegisterRequest;
import org.example.lab_nhom_1.entity.User;

public interface IAuthService
{
    User login(LoginRequest request);
    void register(RegisterRequest request, String otp);
    void resetPassword(String email, String newPassword, String otp);
    void sendOtp(String email);
}
