package com.example.frontpet2pet.data.models.request;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("username")  // Para OAuth2PasswordRequestForm
    private String email;
    private String password;

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters
    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
