package com.example.frontpet2pet.data.models.request;

import com.google.gson.annotations.SerializedName;

public class RegisterRequest {
    @SerializedName("user_name")
    private String userName;

    @SerializedName("user_last_name")
    private String userLastName;

    @SerializedName("user_email")
    private String userEmail;

    @SerializedName("password")
    private String password;

    @SerializedName("user_city")
    private String userCity;

    @SerializedName("user_country")
    private String userCountry;

    @SerializedName("user_number")
    private String userNumber;

    @SerializedName("user_bio")
    private String userBio;

    @SerializedName("role_id")
    private Integer roleId;

    // Constructor para los campos obligatorios
    public RegisterRequest(String userName, String userLastName, String userEmail, String password) {
        this.userName = userName;
        this.userLastName = userLastName;
        this.userEmail = userEmail;
        this.password = password;
    }

    // Setters para campos opcionales
    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
