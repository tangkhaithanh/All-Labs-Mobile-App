package com.example.lab_nhom_1.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.view.PixelCopy;
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

public class OtpActivity extends AppCompatActivity {
    private EditText otpEditText;
    private Button confirmOtpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        otpEditText=findViewById(R.id.otpEditText);
        confirmOtpButton=findViewById(R.id.confirmOtpButton);

        // lấy email từ intent:

        RegisterRequest request = (RegisterRequest) getIntent().getSerializableExtra("request"); // Dùng getSerializableExtra nếu RegisterRequest implements Serializable
        confirmOtpButton.setOnClickListener(v->{
            String otp = otpEditText.getText().toString();
            if (otp.isEmpty()) {
                Toast.makeText(OtpActivity.this, "Vui lòng nhập OTP", Toast.LENGTH_SHORT).show();
                return;
            }
            // call api để xác nhận:
            register(request, otp);
        });
    }
    private void register(RegisterRequest request, String otp) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);

        apiService.register(request, otp).enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String message = response.body().getMessage();
                    Toast.makeText(OtpActivity.this, message, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(OtpActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    // Xử lý khi đăng ký thành công, ví dụ: chuyển về màn hình đăng nhập
                    finish(); // Đóng màn hình OTP
                } else {
                    Toast.makeText(OtpActivity.this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {
                // Xử lý lỗi khi gọi API thất bại
                Toast.makeText(OtpActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
