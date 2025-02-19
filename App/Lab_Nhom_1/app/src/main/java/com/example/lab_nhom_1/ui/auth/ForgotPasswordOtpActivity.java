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

public class ForgotPasswordOtpActivity extends AppCompatActivity {
    private EditText otpEditText;
    private Button confirmOtpButton;
    private String email; // Nhận email từ Intent

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText = findViewById(R.id.otpEditText);
        confirmOtpButton = findViewById(R.id.confirmOtpButton);

        // Lấy email từ Intent
        email = getIntent().getStringExtra("email");

        // Xử lý sự kiện khi nhấn nút xác nhận
        confirmOtpButton.setOnClickListener(v -> {
            String otp = otpEditText.getText().toString().trim();

            if (otp.isEmpty()) {
                Toast.makeText(ForgotPasswordOtpActivity.this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
                return;
            }

            // Gọi API để xác nhận OTP
            setOtpStatus(email, otp);
        });
    }

    private void setOtpStatus(String email, String otp) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        // Gọi API Validate OTP
        Call<MessageResponse> validateOtpCall = apiService.ValidateOtp(otp);
        validateOtpCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Nếu OTP hợp lệ, gọi tiếp API Set OTP Status
                    Call<MessageResponse> setOtpStatusCall = apiService.SetOtpStatus(email, otp);
                    setOtpStatusCall.enqueue(new Callback<MessageResponse>() {
                        @Override
                        public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                            if (response.isSuccessful() && response.body() != null) {
                                // Hiển thị thông báo thành công
                                String message = response.body().getMessage();
                                Toast.makeText(ForgotPasswordOtpActivity.this, message, Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPasswordOtpActivity.this, ForgotPasswordActivity.class);
                                intent.putExtra("email", email); // Truyền email sang màn hình tiếp theo nếu cần
                                intent.putExtra("otp",otp);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ForgotPasswordOtpActivity.this, "Không thể cập nhật trạng thái OTP", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageResponse> call, Throwable t) {
                            Toast.makeText(ForgotPasswordOtpActivity.this, "Lỗi kết nối khi cập nhật OTP: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // OTP không hợp lệ
                    Toast.makeText(ForgotPasswordOtpActivity.this, "OTP không hợp lệ hoặc đã hết hạn", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                Toast.makeText(ForgotPasswordOtpActivity.this, "Lỗi kết nối khi kiểm tra OTP: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
