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
        TextView textViewIniciarSesion = findViewById(R.id.textView13);
        //Se comenta, en lo que validamos el proceso de calendario como lo utilizartemos  etDate = findViewById(R.id.etDate);
        edtNombre = findViewById(R.id.edtNombre);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword1 = findViewById(R.id.edtPassword1);
        edtPasswordConfirm = findViewById(R.id.edtpasswordConfirm);

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

        // Mostrar el DatePickerDialog (Calendario) al hacer clic en el EditText
        etDate.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Crear el DatePickerDialog (Calendario) y configurarlo en español
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, monthOfYear, dayOfMonth) -> {
            // Configura el formato de la fecha en español
            Calendar selectedDate = Calendar.getInstance();
            selectedDate.set(year1, monthOfYear, dayOfMonth);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", new Locale("es", "ES"));
            etDate.setText(dateFormat.format(selectedDate.getTime()));
        }, year, month, day);

        // Solo mayores de 18 años
        Calendar eighteenYearsAgo = Calendar.getInstance();
        eighteenYearsAgo.add(Calendar.YEAR, -18);
        datePickerDialog.getDatePicker().setMaxDate(eighteenYearsAgo.getTimeInMillis());

        datePickerDialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    private boolean validateForm() {
        String nombre = edtNombre.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword1.getText().toString().trim();
        String confirmPassword = edtPasswordConfirm.getText().toString().trim();
        String fechaNacimiento = etDate.getText().toString().trim();

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || fechaNacimiento.isEmpty()) {
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
