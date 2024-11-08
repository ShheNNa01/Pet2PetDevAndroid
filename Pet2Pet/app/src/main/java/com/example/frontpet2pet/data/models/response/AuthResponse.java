package com.example.frontpet2pet.data.models.response;

import com.google.gson.annotations.SerializedName;

public class AuthResponse {
    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("token_type")
    private String tokenType;
    @SerializedName("expires_in")
    private int expiresIn;
    private UserResponse user;

    // Getters
    public String getAccessToken() { return accessToken; }
    public UserResponse getUser() { return user; }
}
