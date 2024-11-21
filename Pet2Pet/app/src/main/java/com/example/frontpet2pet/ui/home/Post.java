package com.example.frontpet2pet.ui.home;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Post {
    private String postId;
    private String userId;
    private String petId;
    private String petName;
    private String petProfileImage;
    private String description;
    private String imageUrl;
    private Date timestamp;
    private int likes;
    private List<String> comments;

    // Constructor
    public Post() {
        // Inicializar valores por defecto para evitar nulls
        this.timestamp = new Date();
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    public Post(String postId, String userId, String petId, String petName,
                String petProfileImage, String description, String imageUrl) {
        this.postId = postId;
        this.userId = userId;
        this.petId = petId;
        this.petName = petName;
        this.petProfileImage = petProfileImage;
        this.description = description;
        this.imageUrl = imageUrl;
        this.timestamp = new Date();
        this.likes = 0;
        this.comments = new ArrayList<>();
    }

    // Getters con protección null
    public String getPostId() {
        return postId != null ? postId : "";
    }

    public String getUserId() {
        return userId != null ? userId : "";
    }

    public String getPetId() {
        return petId != null ? petId : "";
    }

    public String getPetName() {
        return petName != null ? petName : "";
    }

    public String getPetProfileImage() {
        return petProfileImage != null ? petProfileImage : "";
    }

    public String getDescription() {
        return description != null ? description : "";
    }

    public String getImageUrl() {
        return imageUrl != null ? imageUrl : "";
    }

    public Date getTimestamp() {
        return timestamp != null ? timestamp : new Date();
    }

    public int getLikes() {
        return likes;
    }

    public List<String> getComments() {
        if (comments == null) {
            comments = new ArrayList<>();
        }
        return comments;
    }

    // Los setters se mantienen igual
    public void setPostId(String postId) {
        this.postId = postId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setPetProfileImage(String petProfileImage) {
        this.petProfileImage = petProfileImage;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp != null ? timestamp : new Date();
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComments(List<String> comments) {
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    // Método para añadir un comentario con verificación
    public void addComment(String comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        if (comment != null) {
            this.comments.add(comment);
        }
    }

    // Método para incrementar likes
    public void incrementLikes() {
        this.likes++;
    }

    // Método para decrementar likes
    public void decrementLikes() {
        if (this.likes > 0) {
            this.likes--;
        }
    }
}