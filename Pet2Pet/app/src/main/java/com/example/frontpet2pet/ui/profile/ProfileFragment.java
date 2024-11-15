package com.example.frontpet2pet.ui.profile;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.frontpet2pet.MainActivity;
import com.example.frontpet2pet.R;

public class ProfileFragment extends Fragment {

    private ImageButton btnZonapet;
    private Button btnEdit; // Declarar el botón como variable de clase

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout para este fragmento
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Inicializar las vistas
        btnZonapet = view.findViewById(R.id.btnzp);
        btnEdit = view.findViewById(R.id.btnEdit); // Inicializar el botón

        // Configurar los listeners
        setupClickListeners();

        return view;
    }

    private void setupClickListeners() {
        // Configurar el botón de la imagen (zonapet)
        btnZonapet.setOnClickListener(v -> {
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                NavController navController = Navigation.findNavController(requireActivity(),
                        R.id.nav_host_fragment_activity_main);
                navController.navigate(R.id.navigation_zonepet);
            }, 1000);
        });

        // Configurar el botón de editar perfil
        if (btnEdit != null) {
            btnEdit.setOnClickListener(v -> {
                try {
                    NavController navController = Navigation.findNavController(requireActivity(),
                            R.id.nav_host_fragment_activity_main);
                    navController.navigate(R.id.action_navigation_profile_to_editProfileFragment);
                } catch (Exception e) {
                    Toast.makeText(getContext(), "Error al navegar: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getContext(), "Error: Botón de editar no encontrado",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /*private RecyclerView rvUserPosts;
    private PostsAdapter postsAdapter;
    private List<Post> postsList;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar RecyclerView
        rvUserPosts = view.findViewById(R.id.rvUserPosts);
        postsList = new ArrayList<>();

        // Configurar Layout Manager - Usamos Linear porque tus posts son cards completas
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvUserPosts.setLayoutManager(layoutManager);

        // Configurar Adapter
        postsAdapter = new PostsAdapter(getContext(), postsList);
        rvUserPosts.setAdapter(postsAdapter);

        // Opcional: Añadir espacio entre items
        rvUserPosts.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                                     @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                outRect.bottom = 8; // Espacio entre posts
            }
        });

        // Cargar posts
        loadUserPosts();
    }

    private void loadUserPosts() {
        // Aquí cargarías los posts del usuario desde tu base de datos
        // y actualizarías postsList
        // Al terminar de cargar:
        postsAdapter.notifyDataSetChanged();
    }
}*/
}