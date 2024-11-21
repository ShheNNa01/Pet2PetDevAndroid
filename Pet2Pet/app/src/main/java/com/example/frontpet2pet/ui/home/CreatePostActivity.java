package com.example.frontpet2pet.ui.home;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.data.local.SharedPrefsManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreatePostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    private ImageView imagePreview;
    private EditText descriptionInput;
    private Button postButton;
    private Uri selectedImageUri;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Inicializar vistas
        imagePreview = findViewById(R.id.imagePreview);
        descriptionInput = findViewById(R.id.descriptionInput);
        postButton = findViewById(R.id.postButton);

        // Verificar permisos al inicio
        checkPermissions();

        // Configurar selector de imagen
        imagePreview.setOnClickListener(v -> {
            if (checkUserSession()) {
                selectImage();
            }
        });

        // Configurar botón de publicar
        postButton.setOnClickListener(v -> {
            if (validateInput(selectedImageUri, descriptionInput.getText().toString())) {
                checkNetworkConnection();
                createPost();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imagePreview.setImageURI(selectedImageUri);
        }
    }

    private void createPost() {
        String description = descriptionInput.getText().toString();
        uploadImageAndCreatePost(selectedImageUri, description);
    }

    private void uploadImageAndCreatePost(Uri imageUri, String description) {
        if (imageUri == null) {
            Toast.makeText(this, "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
            return;
        }

        updatePostStatus("UPLOADING");

        try {
            // Comprimir imagen antes de subir
            compressImage(imageUri);

            // Aquí iría tu lógica de subida de imagen y creación de post
            // Por ejemplo, usando Firebase Storage y Firestore

            // Simulación de éxito (reemplazar con tu lógica real)
            updatePostStatus("SUCCESS");
        } catch (Exception e) {
            updatePostStatus("ERROR");
            e.printStackTrace();
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Subiendo publicación...");
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    private boolean validateInput(Uri imageUri, String description) {
        if (imageUri == null) {
            Toast.makeText(this, "Por favor selecciona una imagen", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (description.trim().isEmpty()) {
            Toast.makeText(this, "Por favor añade una descripción", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void compressImage(Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            // Aquí se podra usar el baos para subir la imagen
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkNetworkConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            Toast.makeText(this, "Por favor verifica tu conexión a internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_CODE
                );
            }
        }
    }

    private void updatePostStatus(String status) {
        switch(status) {
            case "UPLOADING":
                showProgressDialog();
                break;
            case "SUCCESS":
                if (progressDialog != null) progressDialog.dismiss();
                Toast.makeText(this, "¡Publicación exitosa!", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
                break;
            case "ERROR":
                if (progressDialog != null) progressDialog.dismiss();
                Toast.makeText(this, "Error al crear la publicación",
                        Toast.LENGTH_LONG).show();
                break;
        }
    }

    private boolean checkUserSession() {
        if (!SharedPrefsManager.getInstance().isLoggedIn()) {
            Toast.makeText(this, "Sesión expirada, por favor inicia sesión nuevamente",
                    Toast.LENGTH_LONG).show();
            // Aquí se podría redirigir al login
            return false;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}