package com.example.frontpet2pet.data.models.response;

import com.google.gson.annotations.SerializedName;

public class UserResponse {
    @SerializedName("user_id")
    private int userId;
    @SerializedName("user_name")
    private String userName;
    @SerializedName("user_last_name")
    private String userLastName;
    @SerializedName("user_email")
    private String userEmail;
    @SerializedName("user_city")
    private String userCity;
    @SerializedName("user_country")
    private String userCountry;
    @SerializedName("user_number")
    private String userNumber;
    @SerializedName("user_bio")
    private String userBio;
    @SerializedName("profile_picture")
    private String profilePicture;
    @SerializedName("is_active")
    private boolean isActive;
    @SerializedName("role_id")
    private Integer roleId;

    public static class Roles {
        public static final int ADMIN = 1;
        public static final int SUPER_ADMIN = 2;
    }

    // Getters y setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getUserCountry() {
        return userCountry;
    }

    public void setUserCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getUserNumber() {
        return userNumber;
    }

    public void setUserNumber(String userNumber) {
        this.userNumber = userNumber;
    }

    public String getUserBio() {
        return userBio;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public boolean isAdmin() {
        return roleId != null && (roleId == Roles.ADMIN || roleId == Roles.SUPER_ADMIN);
    }

    public boolean isSuperAdmin() {
        return roleId != null && roleId == Roles.SUPER_ADMIN;
    }
}
