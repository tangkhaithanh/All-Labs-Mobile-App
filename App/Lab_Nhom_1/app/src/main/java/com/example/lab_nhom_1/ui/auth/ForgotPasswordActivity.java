package com.example.lab_nhom_1.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nhom_1.R;
import com.example.lab_nhom_1.ui.model.MessageResponse;
import com.example.lab_nhom_1.ui.network.ApiClient;
import com.example.lab_nhom_1.ui.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity
{
    private EditText newPasswordEditText, confirmPasswordEditText;
    private Button confirmPasswordButton;

    private String email; // Email và OTP được truyền từ Intent
    private String otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // Lấy email và OTP từ Intent
        email = getIntent().getStringExtra("email");
        otp = getIntent().getStringExtra("otp");

        // Liên kết với các thành phần giao diện
        newPasswordEditText = findViewById(R.id.newPasswordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        confirmPasswordButton = findViewById(R.id.confirmPasswordButton);

        // Xử lý sự kiện khi nhấn nút Xác nhận
        confirmPasswordButton.setOnClickListener(v -> {
            String newPassword = newPasswordEditText.getText().toString().trim();
            String confirmPassword = confirmPasswordEditText.getText().toString().trim();

            if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(ForgotPasswordActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!newPassword.equals(confirmPassword)) {
                Toast.makeText(ForgotPasswordActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi API reset mật khẩu
            resetPassword(email, newPassword, otp);
        });
    }

    private void resetPassword(String email, String newPassword, String otp) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        Call<MessageResponse> call = apiService.forgotpassword(email, newPassword, otp);
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Hiển thị thông báo thành công
                    String message = response.body().getMessage();
                    Toast.makeText(ForgotPasswordActivity.this, message, Toast.LENGTH_SHORT).show();

                    // Quay lại màn hình đăng nhập
                    Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình hiện tại
                } else {
                    Toast.makeText(ForgotPasswordActivity.this, "Không thể đặt lại mật khẩu. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
