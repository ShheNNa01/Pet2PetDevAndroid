package com.example.frontpet2pet.ui.profile;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.ui.inicio.InicioSesion;

public class ZonaPetFragment extends Fragment {

    private Button btnLogout;
    private ImageButton addPet;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_zonepet, container, false);

        // Inicializar vistas
        addPet = view.findViewById(R.id.add);  // Asegúrate de que el ID coincida
        btnLogout = view.findViewById(R.id.btnLogout);

        // Configurar listeners
        setupClickListeners(view);

        return view;
    }

    private void setupClickListeners(View view) {
        // Navegar al fragmento de registro de mascotas
        addPet.setOnClickListener(v ->
                Navigation.findNavController(view).navigate(R.id.action_zonePetFragment_to_registerPetFragment)
        );

        // Configurar botón de logout
        if (btnLogout != null) {
            btnLogout.setOnClickListener(v -> logout());
        } else {
            Toast.makeText(getContext(), "Error: Botón de logout no encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void logout() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro que deseas cerrar sesión?")
                .setPositiveButton("Sí", (dialog, which) -> performLogout())
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
