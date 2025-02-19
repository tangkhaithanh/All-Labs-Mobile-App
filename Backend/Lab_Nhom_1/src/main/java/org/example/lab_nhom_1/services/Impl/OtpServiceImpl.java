package org.example.lab_nhom_1.services.Impl;

import org.example.lab_nhom_1.entity.Otp;
import org.example.lab_nhom_1.repository.OtpRepository;
import org.example.lab_nhom_1.services.IOtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class OtpServiceImpl implements IOtpService {
    @Autowired
    private OtpRepository otpRepository;

    @Override
    public String generateOtp() {
        return String.format("%06d", new Random().nextInt(999999));
    }

    @Override
    public void saveOtp(String email, String otp) {
        Otp otpEntity = new Otp();
        otpEntity.setEmail(email);
        otpEntity.setOtp(otp);
        otpEntity.setExpiryTime(LocalDateTime.now().plusMinutes(5));
        otpEntity.setUsed(false);
        otpRepository.save(otpEntity);
    }

    @Override
    public boolean validateOtp(String otp) {
        Optional<Otp> optionalOtp = otpRepository.findByOtp(otp);
        if (optionalOtp.isEmpty()) {
            return false;
        }
        Otp otpEntity = optionalOtp.get();
        return LocalDateTime.now().isBefore(otpEntity.getExpiryTime());
    }

    @Override
    public boolean setOtpStatus(String email, String otp) {
        if (validateOtp(otp)) {
            Optional<Otp> optionalOtp = otpRepository.findByEmailAndOtpAndUsedFalse(email, otp);
            if (optionalOtp.isPresent()) {
                Otp otpEntity = optionalOtp.get();
                otpEntity.setUsed(true);
                otpRepository.save(otpEntity);
                return true;
            }
        }
        return false;
    }


}
