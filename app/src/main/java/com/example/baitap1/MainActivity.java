package com.example.baitap1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RelativeLayout layout =findViewById(R.id.layout);
        Switch switchBackground = findViewById(R.id.switchBackground);
        layout.setBackgroundResource(R.drawable.pic1);
        Button loginButton = findViewById(R.id.loginButton);

        // đăng ký sự kiện cho switch:
        switchBackground.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Nếu switch được bật, thay đổi hình nền
                    layout.setBackgroundResource(R.drawable.pic3); // Hình nền mới khi bật switch
                } else {
                    // Nếu switch tắt, thay đổi lại hình nền cũ
                    layout.setBackgroundResource(R.drawable.pic1); // Quay lại hình nền mặc định
                }
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}