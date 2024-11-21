package com.example.frontpet2pet.api;

import com.example.frontpet2pet.data.models.request.LoginRequest;
import com.example.frontpet2pet.data.models.request.RegisterRequest;
import com.example.frontpet2pet.data.models.response.AuthResponse;
import com.example.frontpet2pet.data.models.response.PetResponse;
import com.example.frontpet2pet.data.models.response.UserResponse;
import com.example.frontpet2pet.ui.home.Post;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    //Endpoints para login y register
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

    // Endpoints para posts
    @Multipart
    @POST("posts/create")
    Call<Post> createPost(
            @Part MultipartBody.Part image,
            @Part("description") RequestBody description,
            @Part("userId") RequestBody userId,
            @Part("petId") RequestBody petId
    );

    @GET("posts")
    Call<List<Post>> getPosts();
}
