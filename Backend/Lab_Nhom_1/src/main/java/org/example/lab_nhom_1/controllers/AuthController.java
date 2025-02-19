package org.example.lab_nhom_1.controllers;

import org.example.lab_nhom_1.dto.ForgotPasswordRequest;
import org.example.lab_nhom_1.dto.LoginRequest;
import org.example.lab_nhom_1.dto.MessageResponse;
import org.example.lab_nhom_1.dto.RegisterRequest;
import org.example.lab_nhom_1.entity.User;
import org.example.lab_nhom_1.repository.UserRepository;
import org.example.lab_nhom_1.services.IAuthService;
import org.example.lab_nhom_1.services.IOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private IAuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IOtpService otpService;

    // API đăng nhập
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        try
        {
            User user = authService.login(request);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e)
        {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    // API gửi mã OTP
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendRegisterOtp(@RequestParam String email) {
        try
        {
            authService.sendOtp(email);
            return ResponseEntity.ok(new MessageResponse("OTP sent successfully"));
        } catch (Exception e)
        {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    // API đăng ký:
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request,
                                      @RequestParam String otp) {
        try {
            authService.register(request, otp);
            return ResponseEntity.ok(new MessageResponse("User registered successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    // API quên mật khẩu:
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestParam String email,
                                           @RequestParam String newPassword,
                                           @RequestParam String otp) {
        try {
            authService.resetPassword(email, newPassword, otp);
            return ResponseEntity.ok(new MessageResponse("Password reset successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    // API validate Email
    @GetMapping("/email-exists")
    public ResponseEntity<?> emailExists(@RequestParam String email) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }

            boolean exists = userRepository.findByEmail(email).isPresent();

            if (exists) {
                return ResponseEntity.ok(new MessageResponse("Let's input the OTP"));
            } else {
                return ResponseEntity.status(404).body(new MessageResponse("Email does not exist in the system"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    // API validate OTP:
    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestParam String otp) {
        try {
            if (otp == null || otp.trim().isEmpty()) {
                throw new IllegalArgumentException("OTP cannot be empty");
            }

            boolean isValid = otpService.validateOtp(otp);

            if (isValid) {
                return ResponseEntity.ok(new MessageResponse("OTP is valid"));
            } else {
                return ResponseEntity.status(400).body(new MessageResponse("Invalid or expired OTP"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
    @PostMapping("/set-otp-status")
    public ResponseEntity<?> setOtpStatus(@RequestParam String email, @RequestParam String otp) {
        try {
            if (email == null || email.trim().isEmpty()) {
                throw new IllegalArgumentException("Email cannot be empty");
            }
            if (otp == null || otp.trim().isEmpty()) {
                throw new IllegalArgumentException("OTP cannot be empty");
            }

            boolean isStatusUpdated = otpService.setOtpStatus(email, otp);

            if (isStatusUpdated) {
                return ResponseEntity.ok(new MessageResponse("OTP status updated successfully"));
            } else {
                return ResponseEntity.status(400).body(new MessageResponse("Invalid or expired OTP"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse(e.getMessage()));
        }
    }
}
