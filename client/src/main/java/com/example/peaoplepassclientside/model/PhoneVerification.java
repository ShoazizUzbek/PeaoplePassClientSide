package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

public class PhoneVerification {

    @SerializedName("codeHash")
    String codeHash;
    @SerializedName("showAddProfile")
    Boolean showAddProfile;

    public String getCodeHash() {
        return codeHash;
    }

    public void setCodeHash(String codeHash) {
        this.codeHash = codeHash;
    }

    public Boolean getShowAddProfile() {
        return showAddProfile;
    }

    public void setShowAddProfile(Boolean showAddProfile) {
        this.showAddProfile = showAddProfile;
    }
}
