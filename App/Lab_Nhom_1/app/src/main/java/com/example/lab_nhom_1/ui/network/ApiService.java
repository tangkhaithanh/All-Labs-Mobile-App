package com.example.lab_nhom_1.ui.network;

import com.example.lab_nhom_1.ui.model.LoginRequest;
import com.example.lab_nhom_1.ui.model.MessageResponse;
import com.example.lab_nhom_1.ui.model.RegisterRequest;
import com.example.lab_nhom_1.ui.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/auth/login")
    Call<User> login(@Body LoginRequest loginRequest);

    @POST("/api/auth/send-otp")
    Call<MessageResponse> sendOtp(@Query("email") String email);

    @POST("/api/auth/register")
    Call<MessageResponse> register(@Body RegisterRequest request, @Query("otp") String otp);

    @POST("/api/auth/reset-password")
    Call<MessageResponse> forgotpassword(@Query("email") String email, @Query("newPassword") String newPassword, @Query("otp") String otp);

    @GET("/api/auth/email-exists")
    Call<MessageResponse> emailExists(@Query("email") String email);

    @POST("/api/auth/set-otp-status")
    Call<MessageResponse> SetOtpStatus(@Query("email") String email, @Query("otp") String otp);

    @POST("/api/auth/validate-otp")
    Call<MessageResponse>ValidateOtp(@Query("otp") String otp);
}
