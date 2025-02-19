package com.example.lab_nhom_1.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_nhom_1.R;
import com.example.lab_nhom_1.ui.model.MessageResponse;
import com.example.lab_nhom_1.ui.network.ApiClient;
import com.example.lab_nhom_1.ui.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendOtpActivity extends AppCompatActivity {
    private EditText emailEditText;
    private Button sendOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_email);
        emailEditText=findViewById(R.id.emailEditText);
        sendOtp= findViewById(R.id.sendOtpButton);

        // xử lý sự kiện khi người dùng ấn nút gửi otp:

        sendOtp.setOnClickListener(v -> {
            String email=emailEditText.getText().toString();
            if (email.isEmpty()){
                Toast.makeText(SendOtpActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }
            sendOtp(email);
        });

    }
    private void sendOtp(String email) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Kiểm tra email có tồn tại trước khi gửi OTP
        Call<MessageResponse> checkEmailCall = apiService.emailExists(email);
        checkEmailCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Email tồn tại, tiếp tục gửi OTP
                    Call<MessageResponse> sendOtpCall = apiService.sendOtp(email);
                    sendOtpCall.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                String message = response.body().getMessage();
                                Toast.makeText(SendOtpActivity.this, message, Toast.LENGTH_SHORT).show();

                                // Chuyển sang màn hình OTP
                                Intent intent = new Intent(SendOtpActivity.this, ForgotPasswordOtpActivity.class);
                                intent.putExtra("email", email);
                                startActivity(intent);
                            } else {
                                Toast.makeText(SendOtpActivity.this, "Không thể gửi OTP. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(SendOtpActivity.this, "Lỗi kết nối khi gửi OTP: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Email không tồn tại
                    Toast.makeText(SendOtpActivity.this, "Email không tồn tại trong hệ thống.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(SendOtpActivity.this, "Lỗi kết nối khi kiểm tra email: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
