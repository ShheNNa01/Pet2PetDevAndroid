package com.example.frontpet2pet.ui.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.frontpet2pet.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;
    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment() {
        // Constructor vacío requerido
    }

    @SuppressLint("MissingInflatedId")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragment
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // Inicializar vistas
        recyclerView = root.findViewById(R.id.recyclerView);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postList);
        recyclerView.setAdapter(postAdapter);

        // Configurar SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener(this::loadPosts);

        // Configurar FAB para crear nuevo post
        // En HomeFragment.java
        FloatingActionButton fab = root.findViewById(R.id.fabCreatePost);
        fab.setOnClickListener(v -> {
            // Usar intent directo en lugar de navegación
            try {
                Intent intent = new Intent(getActivity(), CreatePostActivity.class);
                startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error al abrir la pantalla", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        });

        // Cargar posts iniciales
        loadPosts();

        return root;
    }

    private void loadPosts() {
        swipeRefreshLayout.setRefreshing(true);

        // Aquí irá la llamada a tu API cuando esté lista
        // Por ahora, usamos datos de ejemplo
        postList.clear();

        // Crear nuevos posts usando el constructor correcto
        postList.add(new Post(
                "post1",          // postId
                "user1",          // userId
                "pet1",           // petId
                "@thor.criollo",  // petName
                "",              // petProfileImage
                "Una mañana de paseo con mi amo 🦴❤️", // description
                String.valueOf(R.drawable.post1)    // imageUrl
        ));

        postList.add(new Post(
                "post2",
                "user2",
                "pet2",
                "@michubarbilla",
                "",
                "🎃 Feliz halloween a toda la comunidad felina 🐈",
                String.valueOf(R.drawable.post2)
        ));

        postList.add(new Post(
                "post3",
                "user3",
                "pet3",
                "@macondo.gov",
                "",
                "¡La salud de tu hogar está en tus manos!",
                String.valueOf(R.drawable.post3)
        ));

        postList.add(new Post(
                "post4",
                "user4",
                "pet4",
                "@thebigpig",
                "",
                "En un gran día soleado ☀️",
                String.valueOf(R.drawable.post4)
        ));

        postList.add(new Post(
                "post5",
                "user5",
                "pet5",
                "@gatubela",
                "",
                "ALERTA: Terremoto en formación",
                String.valueOf(R.drawable.post5)
        ));

        postList.add(new Post(
                "post6",
                "user6",
                "pet6",
                "@susy.horia",
                "",
                "No te pierdas el 5to encuentro consecutivo, tendremos muchas sorpresas 🤠",
                String.valueOf(R.drawable.post6)
        ));

        postAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Recargar posts cuando volvemos al fragmento
        loadPosts();
    }

    private void showError(String message) {
        if (getContext() != null) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }
    }
}