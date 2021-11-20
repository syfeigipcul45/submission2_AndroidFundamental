package com.example.submission2_githubuser.form;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.submission2_githubuser.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        int waktu = 2000;
        super.onCreate(savedInstanceState);
        new Handler(Looper.getMainLooper()).postDelayed(()-> {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
        },waktu);
    }
}
