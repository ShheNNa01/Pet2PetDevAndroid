package com.example.frontpet2pet;

import android.content.Intent;
import android.os.Bundle;
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

    private ActivityMainBinding binding;
    private NavController navController;
    private static final int CREATE_POST_REQUEST = 100; // Añadido para manejar el resultado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificar sesión
        if (!SharedPrefsManager.getInstance().isLoggedIn()) {
            startActivity(new Intent(this, InicioSesion.class));
            finish();
            return;
        }

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
                if (SharedPrefsManager.getInstance().isLoggedIn()) {
                    launchCreatePost();
                } else {
                    Toast.makeText(this, "Debes iniciar sesión primero", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(this, InicioSesion.class));
                }
            });
        }
    }

    private void launchCreatePost() {
        try {
            Intent intent = new Intent(this, CreatePostActivity.class);
            startActivityForResult(intent, CREATE_POST_REQUEST);
        } catch (Exception e) {
            Toast.makeText(this, "Error al abrir la creación de post", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void handleIncomingIntent() {
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("showFragment") != null) {
            String fragmentToShow = intent.getStringExtra("showFragment");
            if (fragmentToShow.equals("inicio_sesion")) {
                navController.navigate(R.id.navigation_iniciar);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_POST_REQUEST) {
            if (resultCode == RESULT_OK) {
                // Recargar el feed o mostrar mensaje de éxito
                Toast.makeText(this, "Post creado exitosamente", Toast.LENGTH_SHORT).show();
                // Opcional: refrescar el fragment de home
                if (navController.getCurrentDestination().getId() == R.id.navigation_home) {
                    navController.navigate(R.id.navigation_home);
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    public void logout() {
        try {
            SharedPrefsManager.getInstance().clearSession();
            Intent intent = new Intent(this, InicioSesion.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Toast.makeText(this, "Error al cerrar sesión", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Verificar sesión cada vez que la actividad vuelve al frente
        if (!SharedPrefsManager.getInstance().isLoggedIn()) {
            startActivity(new Intent(this, InicioSesion.class));
            finish();
        }
    }
}