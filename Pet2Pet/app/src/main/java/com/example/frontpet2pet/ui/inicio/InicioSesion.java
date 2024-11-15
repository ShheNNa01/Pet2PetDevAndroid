package com.example.frontpet2pet.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.frontpet2pet.MainActivity;
import com.example.frontpet2pet.R;
import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.data.models.response.AuthResponse;
import com.example.frontpet2pet.ui.auth.login.LoginViewModel;
import com.example.frontpet2pet.ui.auth.register.RegisterActivity;

import java.util.ArrayList;
import java.util.List;

public class InicioSesion extends AppCompatActivity {

    private LoginViewModel viewModel;
    private TextView textViewCrearCuenta;
    private EditText editTextUsuario, editTextContraseña;
    private Button buttonCrear;
    private View progressOverlay;
    private String welcome_message;
    private TextView ForgotPass;

    // Lista para mantener referencia a los observers
    private List<Observer<?>> observers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);

        initializeViews();
        initializeViewModel();
        setupObservers();
        setupClickListeners();
    }

    private void initializeViews() {
        textViewCrearCuenta = findViewById(R.id.registrar);
        editTextUsuario = findViewById(R.id.editTextText);
        editTextContraseña = findViewById(R.id.editTextTextPassword);
        buttonCrear = findViewById(R.id.buttonCrear);
        progressOverlay = findViewById(R.id.progress_overlay);
        welcome_message = getString(R.string.welcome_message);
        ForgotPass = findViewById(R.id.ForgotPass);
    }

    private void initializeViewModel() {
        viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    }

    private void setupObservers() {
        Observer<Boolean> loadingObserver = isLoading -> {
            if (isLoading != null) {
                progressOverlay.setVisibility(isLoading ? View.VISIBLE : View.GONE);
                buttonCrear.setEnabled(!isLoading);
            }
        };
        viewModel.getIsLoading().observe(this, loadingObserver);
        observers.add(loadingObserver);

        Observer<String> errorObserver = error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        };
        viewModel.getError().observe(this, errorObserver);
        observers.add(errorObserver);

        Observer<AuthResponse> loginObserver = result -> {
            if (result != null) {
                Toast.makeText(this, welcome_message, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(InicioSesion.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        viewModel.getLoginResult().observe(this, loginObserver);
        observers.add(loginObserver);
    }

    private void setupClickListeners() {
        textViewCrearCuenta.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click_alpha_animation));
            startActivity(new Intent(this, RegisterActivity.class));
        });

        buttonCrear.setOnClickListener(v -> {
            String usuario = editTextUsuario.getText().toString().trim();
            String contraseña = editTextContraseña.getText().toString().trim();

            if (validateInput(usuario, contraseña)) {
                viewModel.login(usuario, contraseña);
            }
        });

        ForgotPass.setOnClickListener(v -> {
            v.startAnimation(AnimationUtils.loadAnimation(this, R.anim.click_alpha_animation));
            Intent intent = new Intent(InicioSesion.this, ForgotPas.class);
            startActivity(intent);
        });


    }


    private boolean validateInput(String usuario, String contraseña) {
        if (usuario.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(usuario).matches()) {
            Toast.makeText(this, "Por favor, ingrese un email válido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isFinishing() && viewModel != null) {
            // Remover observers de manera segura
            for (Observer<?> observer : observers) {
                // Usar LiveData específicos
                viewModel.getIsLoading().removeObserver((Observer<Boolean>) observer);
                viewModel.getError().removeObserver((Observer<String>) observer);
                viewModel.getLoginResult().removeObserver((Observer<AuthResponse>) observer);
            }
            observers.clear();
        }
    }



}