package com.example.frontpet2pet.ui.auth.register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.frontpet2pet.MainActivity;
import com.example.frontpet2pet.R;
import com.example.frontpet2pet.ui.inicio.InicioSesion;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText etDate;
    private EditText edtNombre;
    private EditText edtApellido;
    private EditText edtEmail;
    private EditText edtPassword1;
    private EditText edtPasswordConfirm;

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

        // Inicializar vistas
        Button crearCuentaButton = findViewById(R.id.buttonCrear);
        TextView textViewIniciarSesion = findViewById(R.id.tvlogin);
        edtNombre = findViewById(R.id.edtName);
        edtApellido= findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword1 = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtpassConfirm);

        // Evento click para el botón de crear cuenta
        crearCuentaButton.setOnClickListener(v -> {
            if (validateForm()) {
                // Si la validación es exitosa, mostrar mensaje y redirigir
                Toast.makeText(RegisterActivity.this,
                        "Cuenta creada con éxito. Redirigiendo al inicio de sesión...",
                        Toast.LENGTH_SHORT).show();

                v.postDelayed(() -> {
                    Intent intent = new Intent(RegisterActivity.this, InicioSesion.class);
                    startActivity(intent);
                    finish();
                }, 3000);
            }
            // Si la validación falla, ya se mostrarán los mensajes de error en validateForm
        });

        // Configura el clic para el texto de iniciar sesión
        textViewIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("showFragment", "inicio_sesion");
            startActivity(intent);
            finish();
        });


    }



    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean validateForm() {
        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword1.getText().toString().trim();
        String confirmPassword = edtPasswordConfirm.getText().toString().trim();
        String fechaNacimiento = etDate.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || apellido.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos del formulario.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Ingresa un correo electrónico válido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this, "La contraseña debe tener al menos 8 caracteres, incluir un número, una letra mayúscula y un carácter especial.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Validación de contraseña: mínimo 8 caracteres, al menos un número, una letra mayúscula y un carácter especial
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }
}
