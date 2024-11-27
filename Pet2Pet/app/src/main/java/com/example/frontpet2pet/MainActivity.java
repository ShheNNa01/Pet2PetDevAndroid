package com.example.frontpet2pet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.ui.home.CreatePostActivity;
import com.example.frontpet2pet.ui.inicio.InicioSesion;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.frontpet2pet.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int CREATE_POST_REQUEST = 100;

    private ActivityMainBinding binding;
    private NavController navController;
    private boolean isProcessingClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!checkAndHandleUserSession()) {
            return;
        }

        initializeUI();
    }

    private boolean checkAndHandleUserSession() {
        if (!SharedPrefsManager.getInstance().isLoggedIn()) {
            redirectToLogin();
            return false;
        }
        return true;
    }

    private void redirectToLogin() {
        startActivity(new Intent(this, InicioSesion.class));
        finish();
    }

    private void initializeUI() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupNavigation();
        setupFabCreatePost();
        handleIncomingIntent();
    }

    private void setupNavigation() {
        BottomNavigationView navView = binding.navView;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    private void setupFabCreatePost() {
        FloatingActionButton fabCreatePost = binding.fabCreatePost;
        if (fabCreatePost != null) {
            fabCreatePost.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, CreatePostActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "Hora de ponerse creativo", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(this, "Ups..Algo fallo", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            });
        } else {
            Log.e("MainActivity", "FAB es nulo, no se pudo configurar.");
        }
    }

    private void configureFabButton(FloatingActionButton fab) {
        fab.setOnClickListener(null);
        fab.setEnabled(true);
        Log.d(TAG, "FAB configured");

        fab.setOnClickListener(v -> handleFabClick(v));
    }

    private void handleFabClick(View v) {
        if (isProcessingClick) {
            return;
        }

        isProcessingClick = true;
        v.setEnabled(false);
        Log.d(TAG, "FAB clicked");

        try {
            if (!isFinishing() && !isDestroyed()) {
                if (SharedPrefsManager.getInstance().isLoggedIn()) {
                    launchCreatePost();
                } else {
                    handleNotLoggedInUser();
                }
            }
        } catch (Exception e) {
            handleFabError(e);
        } finally {
            resetFabState(v);
        }
    }

    private void launchCreatePost() {
        try {
            // Usar Intent directo en lugar de navegación
            Intent intent = new Intent(this, CreatePostActivity.class);
            startActivity(intent);
        } catch (Exception e) {
            Log.e(TAG, "Error launching CreatePostActivity", e);
            Toast.makeText(this,
                    "Error al abrir la pantalla de crear post",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void handleNotLoggedInUser() {
        Log.d(TAG, "User not logged in, redirecting to login");
        Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
        redirectToLogin();
    }

    private void handleFabError(Exception e) {
        Log.e(TAG, "Error handling FAB click: " + e.getMessage());
        Toast.makeText(this, "Error al abrir la pantalla", Toast.LENGTH_SHORT).show();
        e.printStackTrace();
    }

    private void resetFabState(View v) {
        v.postDelayed(() -> {
            v.setEnabled(true);
            isProcessingClick = false;
        }, 500);
    }

    private void handleIncomingIntent() {
        Intent intent = getIntent();
        if (intent != null && "inicio_sesion".equals(intent.getStringExtra("showFragment"))) {
            navController.navigate(R.id.navigation_iniciar);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_POST_REQUEST && resultCode == RESULT_OK) {
            handleSuccessfulPostCreation();
        }
    }

    private void handleSuccessfulPostCreation() {
        Toast.makeText(this, "Post creado exitosamente", Toast.LENGTH_SHORT).show();
        refreshHomeFragment();
    }

    private void refreshHomeFragment() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_home) {
            navController.navigate(R.id.navigation_home);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    public void logout() {
        try {
            SharedPrefsManager.getInstance().clearSession();
            redirectToLogin();
        } catch (Exception e) {
            Log.e(TAG, "Error during logout: " + e.getMessage());
            Toast.makeText(this, "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAndHandleUserSession();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isProcessingClick = false;
        if (binding != null) {
            binding = null;
        }
    }
}