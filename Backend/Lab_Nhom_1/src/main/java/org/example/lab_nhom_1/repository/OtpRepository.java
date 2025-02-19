package org.example.lab_nhom_1.repository;

import org.example.lab_nhom_1.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface OtpRepository extends JpaRepository<Otp, Long>
{
    Optional<Otp> findByEmailAndOtpAndUsedFalse(String email, String otp);

    Optional<Otp> findByOtp(String otp);
}
