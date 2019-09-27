package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

public class Filters {
    public Filters(State state) {
        this.state = state;
    }

    @SerializedName("state")
    private State state;

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
