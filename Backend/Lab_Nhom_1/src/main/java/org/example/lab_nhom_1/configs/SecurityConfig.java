package org.example.lab_nhom_1.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Cấu hình bảo mật
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/login",
                                "/api/auth/register",
                                "/api/auth/send-otp",
                                "/api/auth/reset-password",
                                "/api/auth/email-exists",
                                "/api/auth/validate-otp",
                                "/api/auth/set-otp-status"
                        ).permitAll() // Cho phép truy cập không cần xác thực
                        .anyRequest().authenticated() // Yêu cầu xác thực cho các endpoint khác
                )
                .csrf(csrf -> csrf.disable()) // Tắt CSRF (nếu không cần cho REST API)
                .httpBasic(Customizer.withDefaults()) // Sử dụng HTTP Basic Authentication (nếu cần)
                .formLogin(Customizer.withDefaults()); // Sử dụng form login (nếu cần)

        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

