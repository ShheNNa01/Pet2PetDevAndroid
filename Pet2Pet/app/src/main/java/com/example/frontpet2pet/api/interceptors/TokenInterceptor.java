package com.example.frontpet2pet.api.interceptors;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;

import com.example.frontpet2pet.Pet2PetApplication;
import com.example.frontpet2pet.data.local.SharedPrefsManager;
import com.example.frontpet2pet.ui.inicio.InicioSesion;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Response response = chain.proceed(original);

        if (response.code() == 401) {
            // Manejar en el hilo principal ya que involucra UI
            new Handler(Looper.getMainLooper()).post(() -> {
                // Limpiar sesión
                SharedPrefsManager.getInstance().clearSession();

                // Crear intent con banderas necesarias
                Intent intent = new Intent(Pet2PetApplication.getAppContext(), InicioSesion.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                Pet2PetApplication.getAppContext().startActivity(intent);
            });
        }

        return response;
    }
}