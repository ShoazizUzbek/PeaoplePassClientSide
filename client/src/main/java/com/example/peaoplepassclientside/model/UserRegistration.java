package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

import java.util.UUID;

public class UserRegistration {
    @SerializedName("phone")
    String phone;
    @SerializedName("surname")
    String surname;
    @SerializedName("name")
    String name;
    @SerializedName("middlename")
    String middlename;
    @SerializedName("email")
    String email;
    @SerializedName("photo")
    UUID photo;
    @SerializedName("hash")
    String hash;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UUID getPhoto() {
        return photo;
    }

    public void setPhoto(UUID photo) {
        this.photo = photo;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
