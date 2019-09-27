package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

public class Sort {



    @SerializedName("visitorName")
    private String visitorName;

    @SerializedName("visitorSurname")
    private String visitorSurname;

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public String getVisitorSurname() {
        return visitorSurname;
    }

    public void setVisitorSurname(String visitorSurname) {
        this.visitorSurname = visitorSurname;
    }

    @SerializedName("visitDate")
    private String visitDate;

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

}
