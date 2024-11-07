package com.example.frontpet2pet.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontpet2pet.MainActivity;
import com.example.frontpet2pet.R;
import com.example.frontpet2pet.RegisterActivity;
import com.example.frontpet2pet.ui.home.HomeFragment;

public class InicioSesion extends AppCompatActivity {

    private TextView textViewCrearCuenta;
    private EditText editTextUsuario, editTextContraseña;
    private Button buttonCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        textViewCrearCuenta = findViewById(R.id.registrar);
        editTextUsuario = findViewById(R.id.editTextText);
        editTextContraseña = findViewById(R.id.editTextTextPassword);
        buttonCrear = findViewById(R.id.buttonCrear);

        textViewCrearCuenta.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(InicioSesion.this, R.anim.click_alpha_animation));
            startActivity(new Intent(InicioSesion.this, RegisterActivity.class));
        });

        buttonCrear.setOnClickListener(v -> {
            String usuario = editTextUsuario.getText().toString().trim();
            String contraseña = editTextContraseña.getText().toString().trim();

            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(InicioSesion.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else if (usuario.equals("admin") && contraseña.equals("Admin1+")) {
                // Suponiendo que la autenticación fue exitosa
                Toast.makeText(InicioSesion.this, "Iniciando sesión...", Toast.LENGTH_SHORT).show();
                // Redirige a MainActivity
                Intent intent = new Intent(InicioSesion.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                // Mensaje de error si los datos son inválidos
                Toast.makeText(InicioSesion.this, "Usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show();
            }

        });
    }
}

