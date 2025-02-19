package org.example.lab_nhom_1.services.Impl;

import org.example.lab_nhom_1.dto.LoginRequest;
import org.example.lab_nhom_1.dto.RegisterRequest;
import org.example.lab_nhom_1.entity.User;
import org.example.lab_nhom_1.repository.UserRepository;
import org.example.lab_nhom_1.services.IAuthService;
import org.example.lab_nhom_1.services.IEmailService;
import org.example.lab_nhom_1.services.IOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IOtpService otpService;

    @Autowired
    private IEmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User login(LoginRequest request) {
        String email = request.getEmail();

        String password = request.getPassword();

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty())
        {
            throw new RuntimeException("User or password is incorrect");
        }

        User user = userOptional.get();

        boolean isPasswordMatch = passwordEncoder.matches(password, user.getPassword());

        if (!isPasswordMatch) {

            throw new RuntimeException("User or password is incorrect");
        }

        return user;
    }

    @Override
    public void register(RegisterRequest request, String otp)
    {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("User already exists");
        }

        if (!otpService.setOtpStatus(request.getEmail(), otp)){
            throw new RuntimeException("Invalid or expired OTP");
        }
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        userRepository.save(user);
    }

    @Override
    public void resetPassword(String email, String newPassword, String otp) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password cannot be empty");
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void sendOtp(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        String otp = otpService.generateOtp();
        otpService.saveOtp(email.trim(), otp.trim());
        emailService.sendOtpEmail(email, otp);
    }
}