package com.example.frontpet2pet.ui.home;

public class Post {
    private String userName;
    private String description;
    private int imageResource;

    public Post(String userName, String description, int imageResource) {
        this.userName = userName;
        this.description = description;
        this.imageResource = imageResource;
    }

    public String getUserName() {
        return userName;
    }

    public String getDescription() {
        return description;
    }

    public int getImageResource() {
        return imageResource;
    }
}
