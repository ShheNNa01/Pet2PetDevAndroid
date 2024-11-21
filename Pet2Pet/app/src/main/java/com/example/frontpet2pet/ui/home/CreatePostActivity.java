package com.example.frontpet2pet.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frontpet2pet.R;

public class CreatePostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1; // Definimos la constante aquí
    private ImageView imagePreview;
    private EditText descriptionInput;
    private Button postButton;
    private Uri selectedImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Inicializar vistas
        imagePreview = findViewById(R.id.imagePreview);
        descriptionInput = findViewById(R.id.descriptionInput);
        postButton = findViewById(R.id.postButton);

        // Configurar selector de imagen
        imagePreview.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Configurar botón de publicar
        postButton.setOnClickListener(v -> createPost());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imagePreview.setImageURI(selectedImageUri);
        }
    }

    private void createPost() {
        String description = descriptionInput.getText().toString();

        if (selectedImageUri == null) {
            Toast.makeText(this, "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        // Aquí implementarías la lógica para subir la imagen y crear el post
        uploadImageAndCreatePost(selectedImageUri, description);
    }

    private void uploadImageAndCreatePost(Uri imageUri, String description) {
        // Por ahora solo mostraremos un mensaje
        Toast.makeText(this, "Implementar lógica de subida", Toast.LENGTH_SHORT).show();
    }
}