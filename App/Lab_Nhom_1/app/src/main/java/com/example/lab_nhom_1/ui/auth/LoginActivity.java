package com.example.lab_nhom_1.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nhom_1.R;
import com.example.lab_nhom_1.ui.model.LoginRequest;
import com.example.lab_nhom_1.ui.model.User;
import com.example.lab_nhom_1.ui.network.ApiClient;
import com.example.lab_nhom_1.ui.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText  emailEditText, passwordEditText;
    private Button loginButton;
    private TextView registerLabel, forgotPasswordLabel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        loginButton = findViewById(R.id.loginButton);
        registerLabel= findViewById(R.id.registerLabel);
        forgotPasswordLabel = findViewById(R.id.forgotPasswordLabel);

        // Xử lý sự kiện cho nút đăng nhập:
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Vui lòng nhập email và mật khẩu", Toast.LENGTH_SHORT).show();
                return;
            }

            // gọi API để đăng nhập:
            ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
            LoginRequest loginRequest = new LoginRequest(email, password);
            Call<User> call= apiService.login(loginRequest);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        // Nếu đăng nhập thành công, chuyển đến màn hình chính
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                       // finish(); // Đóng màn hình LoginActivity
                    } else {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        // Điều hướng tới màn hình đăng ký:
        registerLabel.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Điều hướng tới màn hình quên mật khẩu:
        forgotPasswordLabel.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SendOtpActivity.class);
            startActivity(intent);
        });

    }
}