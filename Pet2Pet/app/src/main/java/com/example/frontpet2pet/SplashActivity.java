package com.example.frontpet2pet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frontpet2pet.ui.inicio.InicioSesion;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIMEOUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            // Redirigir a InicioSesion y pasar el estado de sesión como extra
            Intent intent = new Intent(SplashActivity.this, InicioSesion.class);
            startActivity(intent);
            finish();
        }, SPLASH_TIMEOUT);
    }
}
