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
import com.example.frontpet2pet.api.ApiService;
import com.example.frontpet2pet.api.RetrofitClient;
import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.data.models.response.PostResponse;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PERMISSION_REQUEST_CODE = 2;

    private ImageView imagePreview;
    private EditText descriptionInput;
    private Button postButton;
    private Uri selectedImageUri;
    private ProgressDialog progressDialog;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        // Inicializar ApiService
        apiService = RetrofitClient.getInstance().create(ApiService.class);

        // Inicializar vistas
        initializeViews();

        // Verificar permisos al inicio
        checkPermissions();

        // Configurar listeners
        setupListeners();
    }

    private void initializeViews() {
        imagePreview = findViewById(R.id.imagePreview);
        descriptionInput = findViewById(R.id.descriptionInput);
        postButton = findViewById(R.id.postButton);
    }

    private void setupListeners() {
        imagePreview.setOnClickListener(v -> {
            if (checkUserSession()) {
                selectImage();
            }
        });

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
        if (!validateInput(imageUri, description)) return;

        showProgressDialog();
        updatePostStatus("UPLOADING");

        try {
            // Convertir imagen a archivo
            File imageFile = createImageFile(imageUri);

            // Crear partes del multipart request
            MultipartBody.Part imagePart = createImagePart(imageFile);
            RequestBody descriptionBody = createRequestBody(description);
            RequestBody userIdBody = createRequestBody(SharedPrefsManager.getInstance().getUserId());
            RequestBody petIdBody = createRequestBody(SharedPrefsManager.getInstance().getPetId());

            // Hacer la llamada a la API
            Call<PostResponse> call = apiService.createPost(
                    imagePart,
                    descriptionBody,
                    userIdBody,
                    petIdBody
            );

            call.enqueue(new Callback<PostResponse>() {
                @Override
                public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                    handleApiResponse(response);
                }

                @Override
                public void onFailure(Call<PostResponse> call, Throwable t) {
                    handleApiError(t);
                }
            });

        } catch (IOException e) {
            handleException(e);
        }
    }

    private MultipartBody.Part createImagePart(File file) {
        RequestBody requestFile = RequestBody.create(
                MediaType.parse("image/*"),
                file
        );
        return MultipartBody.Part.createFormData("image", file.getName(), requestFile);
    }

    private RequestBody createRequestBody(String value) {
        return RequestBody.create(MediaType.parse("text/plain"), value != null ? value : "");
    }

    private void handleApiResponse(Response<PostResponse> response) {
        if (response.isSuccessful() && response.body() != null) {
            updatePostStatus("SUCCESS");
        } else {
            String errorMessage = "Error al crear el post";
            try {
                if (response.errorBody() != null) {
                    errorMessage = response.errorBody().string();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            updatePostStatus("ERROR");
            Toast.makeText(CreatePostActivity.this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void handleApiError(Throwable t) {
        updatePostStatus("ERROR");
        Toast.makeText(CreatePostActivity.this,
                "Error de conexión: " + t.getMessage(),
                Toast.LENGTH_LONG).show();
    }

    private void handleException(Exception e) {
        updatePostStatus("ERROR");
        e.printStackTrace();
        Toast.makeText(this, "Error al procesar la imagen", Toast.LENGTH_SHORT).show();
    }

    private File createImageFile(Uri imageUri) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getCacheDir();
        File imageFile = File.createTempFile(imageFileName, ".jpg", storageDir);

        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        FileOutputStream fos = new FileOutputStream(imageFile);
        fos.write(baos.toByteArray());
        fos.close();

        return imageFile;
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