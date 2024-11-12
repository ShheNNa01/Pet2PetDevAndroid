package com.example.frontpet2pet.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.frontpet2pet.R;

public class ZonaPetFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el layout de fragment_zonapet.xml para este fragmento
        View view = inflater.inflate(R.layout.fragment_zonepet, container, false);

        // Configurar clicks para cada ImageButton
        ImageButton profileImage = view.findViewById(R.id.profileImage);
        ImageButton petAslan = view.findViewById(R.id.pet1);
        ImageButton petSky = view.findViewById(R.id.pet2);
        ImageButton petNoche = view.findViewById(R.id.pet3);
        ImageButton petAvril = view.findViewById(R.id.pet4);
        ImageButton addPet = view.findViewById(R.id.add);

        // Navegar a editar perfil
        profileImage.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileFragment.class);
            startActivity(intent);
        });

        // Navegar a detalles de Aslan
        petAslan.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PetDetailsFragment.class);
            intent.putExtra("petName", "Aslan");
            startActivity(intent);
        });

        // Navegar a detalles de Sky
        petSky.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PetDetailsFragment.class);
            intent.putExtra("petName", "Sky");
            startActivity(intent);
        });

        // Navegar a detalles de Noche
        petNoche.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PetDetailsFragment.class);
            intent.putExtra("petName", "Noche");
            startActivity(intent);
        });

        // Navegar a detalles de Avril
        petAvril.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), PetDetailsFragment.class);
            intent.putExtra("petName", "Avril");
            startActivity(intent);
        });

        // Navegar a agregar mascota
        addPet.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddPetFragment.class);
            startActivity(intent);
        });

        return view;
    }
}
