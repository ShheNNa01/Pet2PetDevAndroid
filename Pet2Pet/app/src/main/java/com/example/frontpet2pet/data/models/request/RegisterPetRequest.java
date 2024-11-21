package com.example.frontpet2pet.data.models.request;

public class RegisterPetRequest {

    private String name;
    private String breed;
    private String gender;
    private String birthdate;
    private String biography;

    // Constructor para inicializar los valores
    public RegisterPetRequest(String name, String breed, String gender, String birthdate, String biography) {
        this.name = name;
        this.breed = breed;
        this.gender = gender;
        this.birthdate = birthdate;
        this.biography = biography;
    }

    // Getters y Setters para acceder y modificar los valores
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }
}
