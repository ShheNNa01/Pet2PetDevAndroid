package com.example.frontpet2pet.ui.inicio;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.api.ApiClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPas extends AppCompatActivity {

    private EditText editTextEmail;
    private Button buttonResetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_forgotpass);

        editTextEmail = findViewById(R.id.editTextEmail);
        buttonResetPassword = findViewById(R.id.button);

        buttonResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPasswordReset();
            }
        });
    }

    private void requestPasswordReset() {
        String email = editTextEmail.getText().toString();
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("email", email);

        ApiClient apiClient = ApiClient.getInstance();
        Call<okhttp3.ResponseBody> call = apiClient.getApiService().requestPasswordReset(requestBody);
        call.enqueue(new Callback<okhttp3.ResponseBody>() {
            @Override
            public void onResponse(Call<okhttp3.ResponseBody> call, Response<okhttp3.ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ForgotPas.this, "Password reset request sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ForgotPas.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<okhttp3.ResponseBody> call, Throwable t) {
                Toast.makeText(ForgotPas.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}