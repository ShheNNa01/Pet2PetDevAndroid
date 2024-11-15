package com.example.frontpet2pet.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.frontpet2pet.R;
import com.google.android.material.textfield.TextInputEditText;

public class EditProfileFragment extends Fragment {
    private Button btnSaveChanges;
    private Button btnChangePhoto;
    private TextInputEditText etName, etCurrentPassword, etNewPassword, etConfirmPassword;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);

        // Inicializar vistas
        initViews(view);
        // Configurar listeners
        setupClickListeners();

        return view;
    }

    private void initViews(View view) {
        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
        btnChangePhoto = view.findViewById(R.id.btnChangePhoto);
        etName = view.findViewById(R.id.etName);
        etCurrentPassword = view.findViewById(R.id.etCurrentPassword);
        etNewPassword = view.findViewById(R.id.etNewPassword);
        etConfirmPassword = view.findViewById(R.id.etConfirmPassword);
    }

    private void setupClickListeners() {
        btnSaveChanges.setOnClickListener(v -> {
            // Lógica para guardar los cambios
            Toast.makeText(getContext(), "Guardando cambios...", Toast.LENGTH_SHORT).show();
        });

        btnChangePhoto.setOnClickListener(v -> {
            // Lógica para cambiar la foto
            Toast.makeText(getContext(), "Seleccionar nueva foto...", Toast.LENGTH_SHORT).show();
        });
    }
}