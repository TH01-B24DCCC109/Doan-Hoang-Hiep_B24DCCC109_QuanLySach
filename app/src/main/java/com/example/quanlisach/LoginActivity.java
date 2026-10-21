package com.example.quanlisach;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private TextView tvErrorMessage;
    private Button btnLogin;

    private final String HARDCODED_USER = "admin";
    private final String HARDCODED_PASS = "123456";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tvErrorMessage = findViewById(R.id.tvErrorMessage);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                tvErrorMessage.setText("Vui lòng nhập đầy đủ thông tin!");
                tvErrorMessage.setVisibility(View.VISIBLE);
            } else if (username.equals(HARDCODED_USER) && password.equals(HARDCODED_PASS)) {
                tvErrorMessage.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                tvErrorMessage.setText("Sai tên đăng nhập hoặc mật khẩu");
                tvErrorMessage.setVisibility(View.VISIBLE);
            }
        });
    }
}