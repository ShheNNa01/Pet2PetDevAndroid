package com.example.frontpet2pet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.ui.inicio.InicioSesion;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Inicializar SharedPrefsManager
        if (SharedPrefsManager.getInstance() == null) {
            SharedPrefsManager.init(getApplicationContext());
        }

        new Handler().postDelayed(() -> {
            Intent intent;
            if (SharedPrefsManager.getInstance().isLoggedIn()) {
                // Si hay sesión activa, ir directo a MainActivity
                intent = new Intent(SplashActivity.this, MainActivity.class);
            } else {
                // Si no hay sesión, ir a InicioSesion
                intent = new Intent(SplashActivity.this, InicioSesion.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_TIMEOUT);
    }
}
