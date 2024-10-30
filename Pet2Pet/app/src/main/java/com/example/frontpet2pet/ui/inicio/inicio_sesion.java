package com.example.frontpet2pet.ui.inicio;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.RegisterActivity;

public class inicio_sesion extends Fragment {

    private TextView textViewCrearCuenta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_inicio_sesion, container, false);

        // Enlazamos el TextView del layout
        textViewCrearCuenta = view.findViewById(R.id.registrar);

        if (textViewCrearCuenta != null) {
            textViewCrearCuenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        // Inicia la actividad RegisterActivity
                        Intent intent = new Intent(getActivity(), RegisterActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {

                        Toast.makeText(getActivity(),
                                "Error al abrir la página de registro",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {

            Toast.makeText(getActivity(), "No se encontró el TextView", Toast.LENGTH_SHORT).show();
        }

        // Añado un efecto para cuando le den click
        textViewCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.click_alpha_animation));
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

        return view;

    }



}
