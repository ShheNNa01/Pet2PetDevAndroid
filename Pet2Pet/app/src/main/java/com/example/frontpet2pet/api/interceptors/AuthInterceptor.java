package com.example.frontpet2pet.api.interceptors;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import com.example.frontpet2pet.data.local.SharedPrefsManager;

public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder();

        // Solo añadir el token si existe
        try {
            String token = SharedPrefsManager.getInstance().getToken();
            if (token != null) {
                builder.header("Authorization", "Bearer " + token);
            }
        } catch (IllegalStateException e) {
            // Si SharedPrefsManager no está inicializado, continuar sin token
        }

        return chain.proceed(builder.build());
    }
}