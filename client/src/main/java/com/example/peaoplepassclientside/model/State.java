package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

public class State {
    public State(String type, String value) {
        this.type = type;
        this.value = value;
    }

    @SerializedName("type")
    private String type;
    @SerializedName("value")
    private String value;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
