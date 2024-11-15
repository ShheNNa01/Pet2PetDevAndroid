package com.example.frontpet2pet.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.ui.inicio.InicioSesion;

public class ZonaPetFragment extends Fragment {

    private Button btnLogout; // Declaración correcta como variable de clase
    private ImageButton profileImage, petAslan, petSky, petNoche, petAvril, addPet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout de fragment_zonapet.xml para este fragmento
        View view = inflater.inflate(R.layout.fragment_zonepet, container, false);

        // Inicializar las vistas
        initializeViews(view);
        // Configurar los listeners
        setupClickListeners();

        return view;
    }

    private void initializeViews(View view) {
        // Inicializar ImageButtons
        profileImage = view.findViewById(R.id.profileImage);
        petAslan = view.findViewById(R.id.pet1);
        petSky = view.findViewById(R.id.pet2);
        petNoche = view.findViewById(R.id.pet3);
        petAvril = view.findViewById(R.id.pet4);
        addPet = view.findViewById(R.id.add);
        btnLogout = view.findViewById(R.id.btnLogout);
    }

    private void setupClickListeners() {
        // Navegar a editar perfil
        profileImage.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(getActivity(), EditProfileFragment.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error al abrir edición de perfil", Toast.LENGTH_SHORT).show();
            }
        });

        // Navegar a detalles de mascotas
        petAslan.setOnClickListener(v -> navigateToPetDetails("Aslan"));
        petSky.setOnClickListener(v -> navigateToPetDetails("Sky"));
        petNoche.setOnClickListener(v -> navigateToPetDetails("Noche"));
        petAvril.setOnClickListener(v -> navigateToPetDetails("Avril"));

        // Navegar a agregar mascota
        addPet.setOnClickListener(v -> {
            try {
                Intent intent = new Intent(getActivity(), AddPetFragment.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error al abrir agregar mascota", Toast.LENGTH_SHORT).show();
            }
        });

        // Configurar el botón de logout
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> logout());
        } else {
            Toast.makeText(getContext(), "Error: Botón de logout no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToPetDetails(String petName) {
        try {
            Intent intent = new Intent(getActivity(), PetDetailsFragment.class);
            intent.putExtra("petName", petName);
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al abrir detalles de " + petName, Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        // Mostrar diálogo de confirmación
        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // Si el usuario confirma, proceder con el cierre de sesión
                    performLogout();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void performLogout() {
        try {
            // Limpiar datos de sesión
            SharedPreferences preferences = requireActivity()
                    .getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            // Navegar a la pantalla de inicio de sesión
            Intent intent = new Intent(getActivity(), InicioSesion.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

            // Cerrar la actividad actual
            if (getActivity() != null) {
                getActivity().finish();
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al cerrar sesión: " + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}