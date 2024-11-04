package com.example.frontpet2pet.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.RegisterActivity;

public class InicioSesion extends AppCompatActivity {

    private TextView textViewCrearCuenta;
    private EditText editTextUsuario, editTextContraseña;
    private Button buttonCrear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        // Enlazamos los elementos del layout
        textViewCrearCuenta = findViewById(R.id.registrar);
        editTextUsuario = findViewById(R.id.editTextText);
        editTextContraseña = findViewById(R.id.editTextTextPassword);
        buttonCrear = findViewById(R.id.buttonCrear);

        // Listener para crear cuenta
        textViewCrearCuenta.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(InicioSesion.this, R.anim.click_alpha_animation));
            startActivity(new Intent(InicioSesion.this, RegisterActivity.class));
        });

        // Listener para el botón de iniciar sesión
        buttonCrear.setOnClickListener(v -> {
            String usuario = editTextUsuario.getText().toString().trim();
            String contraseña = editTextContraseña.getText().toString().trim();

            // Validar campos
            if (usuario.isEmpty() || contraseña.isEmpty()) {
                Toast.makeText(InicioSesion.this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            } else {
                // Aquí puedes implementar la lógica de inicio de sesión
                Toast.makeText(InicioSesion.this, "Iniciando sesión...", Toast.LENGTH_SHORT).show();
                // Redirigir a la actividad principal
            }
        });
    }
}
