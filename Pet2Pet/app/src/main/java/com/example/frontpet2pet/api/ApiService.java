package com.example.frontpet2pet.api;

import com.example.frontpet2pet.data.models.request.LoginRequest;
import com.example.frontpet2pet.data.models.request.RegisterRequest;
import com.example.frontpet2pet.data.models.response.AuthResponse;
import com.example.frontpet2pet.data.models.response.PetResponse;
import com.example.frontpet2pet.data.models.response.UserResponse;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @FormUrlEncoded
    @POST("auth/login")
    Call<AuthResponse> login(
            @Field("username") String email,
            @Field("password") String password
    );

    @POST("auth/register")
    Call<UserResponse> register(@Body RegisterRequest request);

    @POST("auth/password-reset-request")
    Call<ResponseBody> requestPasswordReset(@Body Map<String, String> requestBody);
}
