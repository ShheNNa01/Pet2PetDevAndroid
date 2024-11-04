package com.example.frontpet2pet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.frontpet2pet.ui.inicio.InicioSesion;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_activity_register);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Mostrar la flecha de retroceso
        getSupportActionBar().setTitle("Registrar"); // Establecer el título de la actividad

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button crearCuentaButton = findViewById(R.id.buttonCrear);
        TextView textViewIniciarSesion = findViewById(R.id.textView13); // Asegúrate de que este ID sea correcto

        // Asigna el evento de clic al botón de crear cuenta
        crearCuentaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Muestra un Toast con el mensaje de éxito
                Toast.makeText(RegisterActivity.this,
                        "Cuenta creada con éxito. Redirigiendo al inicio de sesión...",
                        Toast.LENGTH_SHORT).show();

                // Redirige al inicio de sesión después de 2 segundos
                v.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(RegisterActivity.this, InicioSesion.class);
                        startActivity(intent);
                        finish();
                    }
                }, 2000);
            }
        });

        // Configura el clic para textView13
        textViewIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("showFragment", "inicio_sesion"); // Pasa un extra para indicar que quieres mostrar el fragmento
                startActivity(intent);
                finish(); // Opcional: cierra la actividad actual
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
