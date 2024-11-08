package com.example.frontpet2pet.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.frontpet2pet.MainActivity;
import com.example.frontpet2pet.R;
import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.data.models.response.AuthResponse;
import com.example.frontpet2pet.ui.auth.login.LoginViewModel;
import com.example.frontpet2pet.ui.auth.register.RegisterActivity;

public class InicioSesion extends AppCompatActivity {

    private LoginViewModel viewModel;
    private TextView textViewCrearCuenta, textViewOlvidePassword;
    private EditText editTextUsuario, editTextContraseña;
    private Button buttonCrear;
    private View progressOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        // Inicializar SharedPrefsManager si no se ha hecho
        if (SharedPrefsManager.getInstance() == null) {
            SharedPrefsManager.init(getApplicationContext());
        }

        // Verificar si ya hay una sesión activa
        if (SharedPrefsManager.getInstance().getToken() != null) {
            navigateToMain();
            return;
        }

        initializeViews();
        initializeViewModel();
        setupObservers();
        setupClickListeners();
    }

    private void initializeViews() {
        textViewCrearCuenta = findViewById(R.id.registrar);
        textViewOlvidePassword = findViewById(R.id.textView8);
        editTextUsuario = findViewById(R.id.editTextText);
        editTextContraseña = findViewById(R.id.editTextTextPassword);
        buttonCrear = findViewById(R.id.buttonCrear);
        progressOverlay = findViewById(R.id.progress_overlay);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void setupObservers() {
        viewModel.getIsLoading().observe(this, this::handleLoadingState);
        viewModel.getError().observe(this, this::handleError);
        viewModel.getLoginResult().observe(this, this::handleLoginSuccess);
    }

    private void handleLoadingState(Boolean isLoading) {
        progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        buttonCrear.setEnabled(!isLoading);
        editTextUsuario.setEnabled(!isLoading);
        editTextContraseña.setEnabled(!isLoading);
    }

    private void handleError(String error) {
        if (error != null && !error.isEmpty()) {
            Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        }
    }

    private void handleLoginSuccess(AuthResponse result) {
        if (result != null) {
            String welcomeMessage = getString(R.string.welcome_message,
                    result.getUser() != null ? result.getUser().getUserName() : "");
            Toast.makeText(this, welcomeMessage, Toast.LENGTH_SHORT).show();
            navigateToMain();
        }
    }

    private void setupClickListeners() {
        textViewCrearCuenta.setOnClickListener(this::handleRegistroClick);
        buttonCrear.setOnClickListener(this::handleLoginClick);
        textViewOlvidePassword.setOnClickListener(this::handleOlvidePasswordClick);
    }

    private void handleRegistroClick(View v) {
        v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click_alpha_animation));
        startActivity(new Intent(this, RegisterActivity.class));
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void handleLoginClick(View v) {
        String email = editTextUsuario.getText().toString().trim();
        String password = editTextContraseña.getText().toString().trim();

        if (validateInput(email, password)) {
            viewModel.login(email, password);
        }
    }

    private void handleOlvidePasswordClick(View v) {
        // TODO: Implementar recuperación de contraseña
        Toast.makeText(this, "Función en desarrollo", Toast.LENGTH_SHORT).show();
    }

    private boolean validateInput(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            showError(getString(R.string.error_campos_vacios));
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showError(getString(R.string.error_email_invalido));
            return false;
        }

        if (password.length() < 8) {
            showError(getString(R.string.error_password_corto));
            return false;
        }

        return true;
    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiamos los observers para evitar memory leaks
        viewModel.getIsLoading().removeObservers(this);
        viewModel.getError().removeObservers(this);
        viewModel.getLoginResult().removeObservers(this);
    }
}