package com.example.frontpet2pet.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.ui.inicio.InicioSesion;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    public HomeFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);


        // Configurar RecyclerView
        recyclerView = root.findViewById(R.id.recyclerView_feed);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Crear lista de publicaciones (esto vendrá del backend en el futuro)
        postList = new ArrayList<>();
        postList.add(new Post("@thor.criollo", "Una mañana de paseo con mi amo 🦴❤️", R.drawable.post1));
        postList.add(new Post("@michubarbilla", "🎃 Feliz halloween a toda la comunidad felina 🐈", R.drawable.post2));
        postList.add(new Post("@macondo.gov", "¡La salud de tu hogar está en tus manos!", R.drawable.post3));
        postList.add(new Post("@thebigpig", "En un gran día soleado ☀️", R.drawable.post4));
        postList.add(new Post("@gatubela", "ALERTA: Terremoto en formación ", R.drawable.post5));
        postList.add(new Post("@susy.horia", "No te pierdas el 5to encuentro consecutivo, tendremos muchas sorpresas 🤠", R.drawable.post6));

        // Configurar adapter y asociarlo con el RecyclerView
        postAdapter = new PostAdapter(postList);
        recyclerView.setAdapter(postAdapter);

        return root;
    }
}
