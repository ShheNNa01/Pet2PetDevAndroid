package com.example.frontpet2pet.api;

import com.example.frontpet2pet.api.interceptors.AuthInterceptor;
import com.example.frontpet2pet.api.interceptors.TokenInterceptor;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "http://10.0.2.2:8000/api/v1/"; // Para emulador
    private static ApiClient instance;
    private final ApiService apiService;

    private ApiClient() {
        // Logging interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Cliente HTTP con interceptors
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .addInterceptor(new TokenInterceptor())
                .addInterceptor(loggingInterceptor)
                .build();

        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized ApiClient getInstance() {
        if (instance == null) {
            instance = new ApiClient();
        }
        return instance;
    }

    public ApiService getApi() {
        return apiService;
    }
}