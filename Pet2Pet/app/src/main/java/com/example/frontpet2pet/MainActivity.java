package com.example.frontpet2pet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.ui.home.CreatePostActivity;
import com.example.frontpet2pet.ui.inicio.InicioSesion;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.frontpet2pet.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

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

        //Configuración de la navegación
        BottomNavigationView navView = binding.navView;
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        //Conexión del FabCreatePost (button) con la actividad del CreatePostActivity.

        FloatingActionButton fabCreatePost = findViewById(R.id.fabCreatePost);
        fabCreatePost.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatePostActivity.class);
            startActivity(intent);
        });


        // Manejar el Intent que indica qué fragmento mostrar
        Intent intent = getIntent();
        if (intent != null && intent.getStringExtra("showFragment") != null) {
            String fragmentToShow = intent.getStringExtra("showFragment");
            if (fragmentToShow.equals("inicio_sesion")) {
                navController.navigate(R.id.navigation_iniciar);
            }
        }

        // Configuración del botón del logo para navegar al fragmento de inicio de sesión (LO COMENTO MIENTRAS DEFINIMOS EL PROCESO DEL LOGRO)
        //ImageView centerLogoButton = findViewById(R.id.center_logo_button);
        //centerLogoButton.setOnClickListener(v -> {
        // Navegar al fragmento de inicio de sesión
        //navController.navigate(R.id.navigation_iniciar); // Cambia este ID al correcto
        //});
    }
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        return navController.navigateUp() || super.onSupportNavigateUp();
    }

    //Metodo para cerrar sesion
    public void logout() {
        SharedPrefsManager.getInstance().clearSession();
        Intent intent = new Intent(this, InicioSesion.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}








