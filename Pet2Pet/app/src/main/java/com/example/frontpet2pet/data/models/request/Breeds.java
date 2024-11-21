package com.example.frontpet2pet.data.models.request;

import com.google.gson.annotations.SerializedName;

public class Breeds {

    @SerializedName("breed_id")
    private int breedId;

    @SerializedName("breed_name")
    private String breedName;

    @SerializedName("pet_type_id")
    private int petTypeId;

    @SerializedName("created_at")
    private String createdAt;

    // Getters y Setters

    public int getBreedId() {
        return breedId;
    }

    public void setBreedId(int breedId) {
        this.breedId = breedId;
    }

    public String getBreedName() {
        return breedName;
    }

    public void setBreedName(String breedName) {
        this.breedName = breedName;
    }

    public int getPetTypeId() {
        return petTypeId;
    }

    public void setPetTypeId(int petTypeId) {
        this.petTypeId = petTypeId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // Sobrescribir el método toString()
    @Override
    public String toString() {
        return breedName;  // Devuelve el nombre de la raza
    }

    // Constructor

    public Breeds(int breedId, String breedName, int petTypeId, String createdAt) {
        this.breedId = breedId;
        this.breedName = breedName;
        this.petTypeId = petTypeId;
        this.createdAt = createdAt;
    }

    // Constructor vacío (sin parámetros)
    public Breeds() {}
}
