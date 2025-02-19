package com.example.lab_nhom_1.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nhom_1.R;
import com.example.lab_nhom_1.ui.model.MessageResponse;
import com.example.lab_nhom_1.ui.model.RegisterRequest;
import com.example.lab_nhom_1.ui.network.ApiClient;
import com.example.lab_nhom_1.ui.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    private EditText emailEditText, passwordEditText, confirmPasswordEditText, fullnameEditText;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        registerButton = findViewById(R.id.registerButton);
        fullnameEditText= findViewById(R.id.fullNameEditText);

        // xử lý sự kiện ấn nút đăng ký:
         registerButton.setOnClickListener(v -> {
             String email=emailEditText.getText().toString();
             String password= passwordEditText.getText().toString();
             String confirmPassword= confirmPasswordEditText.getText().toString();
             String fullname= fullnameEditText.getText().toString();
             if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fullname.isEmpty()) {
                 Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                 return;
             }
             if (!password.equals(confirmPassword)) {
                 Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                 return;
             }
             RegisterRequest request = new RegisterRequest(email,password,fullname);
             // call api và chuyển sang màn hình nhập otp:
             sendOtp(request);
         });
    }

    // Hàm call api:

    private void sendOtp(RegisterRequest request)
    {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<MessageResponse> call = apiService.sendOtp(request.getEmail());

        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Lấy thông báo từ phản hồi
                    String message = response.body().getMessage();
                    Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_SHORT).show();

                    // Chuyển sang màn hình OTP
                    Intent intent = new Intent(RegisterActivity.this, OtpActivity.class);
                    intent.putExtra("request", request);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Không thể gửi OTP. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
