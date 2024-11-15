package com.example.frontpet2pet.ui.auth.register;

import android.app.ProgressDialog;
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
import com.example.frontpet2pet.api.ApiClient;
import com.example.frontpet2pet.data.models.request.RegisterRequest;
import com.example.frontpet2pet.data.models.response.UserResponse;
import com.example.frontpet2pet.ui.inicio.InicioSesion;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtNombre;
    private EditText edtApellido;
    private EditText edtEmail;
    private EditText edtPassword1;
    private EditText edtPasswordConfirm;
    private Button crearCuentaButton;
    private TextView textViewIniciarSesion;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.fragment_activity_register);

        // Configurar ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Registrar");
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        initializeViews();
        setupListeners();

        // Inicializar ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creando cuenta...");
        progressDialog.setCancelable(false);
    }

    private void initializeViews() {
        crearCuentaButton = findViewById(R.id.buttonCrear);
        textViewIniciarSesion = findViewById(R.id.tvlogin);
        edtNombre = findViewById(R.id.edtName);
        edtApellido = findViewById(R.id.edtLastName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword1 = findViewById(R.id.edtPassword);
        edtPasswordConfirm = findViewById(R.id.edtpassConfirm);
    }

    private void setupListeners() {
        crearCuentaButton.setOnClickListener(v -> {
            if (validateForm()) {
                registerUser();
            }
        });

        textViewIniciarSesion.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            intent.putExtra("showFragment", "inicio_sesion");
            startActivity(intent);
            finish();
        });
    }

    private void registerUser() {
        progressDialog.show();

        // Crear request con los datos del formulario
        RegisterRequest request = new RegisterRequest(
                edtNombre.getText().toString().trim(),
                edtApellido.getText().toString().trim(),
                edtEmail.getText().toString().trim(),
                edtPassword1.getText().toString().trim()
        );

        // Realizar llamada al API
        ApiClient.getInstance().getApi().register(request).enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    showSuccessAndRedirect();
                } else {
                    handleRegistrationError(response.code());
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this,
                        "Error de conexión. Por favor verifica tu conexión a internet.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showSuccessAndRedirect() {
        Toast.makeText(RegisterActivity.this,
                "Cuenta creada con éxito. Por favor verifica tu correo electrónico.",
                Toast.LENGTH_LONG).show();

        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(RegisterActivity.this, InicioSesion.class);
            startActivity(intent);
            finish();
        }, 2000);
    }

    private void handleRegistrationError(int errorCode) {
        String errorMessage = "Error en el registro. Por favor intenta nuevamente.";

        switch (errorCode) {
            case 400:
                errorMessage = "El correo electrónico ya está registrado.";
                break;
            case 422:
                errorMessage = "Por favor verifica los datos ingresados.";
                break;
            case 500:
                errorMessage = "Error en el servidor. Por favor intenta más tarde.";
                break;
        }

        Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_LONG).show();
    }

    private boolean validateForm() {
        String nombre = edtNombre.getText().toString().trim();
        String apellido = edtApellido.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword1.getText().toString().trim();
        String confirmPassword = edtPasswordConfirm.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || email.isEmpty() ||
                password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Por favor, completa todos los campos del formulario.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (nombre.length() < 2 || nombre.length() > 100) {
            Toast.makeText(this, "El nombre debe tener entre 2 y 100 caracteres.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (apellido.length() < 2 || apellido.length() > 100) {
            Toast.makeText(this, "El apellido debe tener entre 2 y 100 caracteres.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidEmail(email)) {
            Toast.makeText(this, "Por favor, ingresa un correo electrónico válido.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Las contraseñas no coinciden.",
                    Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!isValidPassword(password)) {
            Toast.makeText(this,
                    "La contraseña debe tener al menos 8 caracteres, incluir un número, " +
                            "una letra mayúscula y un carácter especial.",
                    Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

    private boolean isValidEmail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=]).{8,}$";
        return Pattern.compile(passwordPattern).matcher(password).matches();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}