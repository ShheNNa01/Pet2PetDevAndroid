package com.example.frontpet2pet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.frontpet2pet.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Manejar el Intent que indica qué fragmento mostrar
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("showFragment") != null) {
            String fragmentToShow = intent.getStringExtra("showFragment");
            if (fragmentToShow.equals("inicio_sesion")) {
                navController.navigate(R.id.navigation_iniciar);
            }
        }

        // Configuración del botón del logo para navegar al fragmento de inicio de sesión
        ImageView centerLogoButton = findViewById(R.id.center_logo_button);
        centerLogoButton.setOnClickListener(v -> {
            // Navegar al fragmento de inicio de sesión
            navController.navigate(R.id.navigation_iniciar); // Cambia este ID al correcto
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}




