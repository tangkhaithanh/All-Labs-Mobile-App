package org.example.lab_nhom_1.services;

public interface IOtpService
{
    String generateOtp();
    void saveOtp(String email, String otp);
    boolean validateOtp(String otp);
    boolean setOtpStatus(String email, String otp);
}
