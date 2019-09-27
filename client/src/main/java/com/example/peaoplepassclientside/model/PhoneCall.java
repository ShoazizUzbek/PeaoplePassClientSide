package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

public class PhoneCall {
    @SerializedName("phone")
    String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
