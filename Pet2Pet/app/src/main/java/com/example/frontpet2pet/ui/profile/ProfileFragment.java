package com.example.frontpet2pet.ui.profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.frontpet2pet.MainActivity;
import com.example.frontpet2pet.R;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Configurar el botón de la imagen (zonapet)
        ImageButton imageButton = view.findViewById(R.id.btnzp);
        imageButton.setOnClickListener(v -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                NavController navController = Navigation.findNavController(requireActivity(),
                        R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_zonepet);
            }, 1000);
        });

        // Configurar el botón de logout
        Button btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).logout();
            }
        });

        // Configurar el botón de editar perfil
        Button btnEdit = view.findViewById(R.id.btnEdit);
        btnEdit.setOnClickListener(v -> {
            // Implementar la lógica para editar perfil
            // Por ejemplo, navegar a un fragmento de edición:
            // NavController navController = Navigation.findNavController(requireActivity(),
            //     R.id.nav_host_fragment_activity_main);
            // navController.navigate(R.id.navigation_edit_profile);
        });

        return view;
    }
}