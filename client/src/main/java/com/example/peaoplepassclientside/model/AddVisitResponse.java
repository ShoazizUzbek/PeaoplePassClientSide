package com.example.peaoplepassclientside.model;


import com.example.peaoplepassclientside.visitorModel.CompanyTo;
import com.example.peaoplepassclientside.visitorModel.VisitTo;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddVisitResponse implements Serializable {
    @SerializedName("id")
    private String id;
    @SerializedName("visitTo")
    private VisitTo visitTo;
    @SerializedName("companyTo")
    private CompanyTo companyTo;
    @SerializedName("visitDate")
    private String visitDate;
    @SerializedName("visitorName")
    private String visitorName;
    @SerializedName("visitorSurname")
    private String visitorSurname;
    @SerializedName("visitorMiddlename")
    private String visitorMiddlename;
    @SerializedName("visitorEmail")
    private String visitorEmail;
    @SerializedName("visitorPhone")
    private String visitorPhone;
    @SerializedName("askForPhoto")
    private Boolean askForPhoto;
    @SerializedName("askForDocumentPhoto")
    private Boolean askForDocumentPhoto;
    @SerializedName("askForDocumentData")
    private Boolean askForDocumentData;
    @SerializedName("askForDocumentPlaceData")
    private Boolean askForDocumentPlaceData;
    @SerializedName("askForEmail")
    private Boolean askForEmail;
    @SerializedName("askForMobilePhone")
    private Boolean askForMobilePhone;
    @SerializedName("requireEscort")
    private Boolean requireEscort;
    @SerializedName("signatureSetting")
    private String signatureSetting;
    @SerializedName("state")
    private String state;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VisitTo getVisitTo() {
        return visitTo;
    }

    public void setVisitTo(VisitTo visitTo) {
        this.visitTo = visitTo;
    }

    public CompanyTo getCompanyTo() {
        return companyTo;
    }

    public void setCompanyTo(CompanyTo companyTo) {
        this.companyTo = companyTo;
    }

    public String  getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

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

    public String getVisitorMiddlename() {
        return visitorMiddlename;
    }

    public void setVisitorMiddlename(String visitorMiddlename) {
        this.visitorMiddlename = visitorMiddlename;
    }

    public String getVisitorEmail() {
        return visitorEmail;
    }

    public void setVisitorEmail(String visitorEmail) {
        this.visitorEmail = visitorEmail;
    }

    public String getVisitorPhone() {
        return visitorPhone;
    }

    public void setVisitorPhone(String visitorPhone) {
        this.visitorPhone = visitorPhone;
    }

    public Boolean getAskForPhoto() {
        return askForPhoto;
    }

    public void setAskForPhoto(Boolean askForPhoto) {
        this.askForPhoto = askForPhoto;
    }

    public Boolean getAskForDocumentPhoto() {
        return askForDocumentPhoto;
    }

    public void setAskForDocumentPhoto(Boolean askForDocumentPhoto) {
        this.askForDocumentPhoto = askForDocumentPhoto;
    }

    public Boolean getAskForDocumentData() {
        return askForDocumentData;
    }

    public void setAskForDocumentData(Boolean askForDocumentData) {
        this.askForDocumentData = askForDocumentData;
    }

    public Boolean getAskForDocumentPlaceData() {
        return askForDocumentPlaceData;
    }

    public void setAskForDocumentPlaceData(Boolean askForDocumentPlaceData) {
        this.askForDocumentPlaceData = askForDocumentPlaceData;
    }

    public Boolean getAskForEmail() {
        return askForEmail;
    }

    public void setAskForEmail(Boolean askForEmail) {
        this.askForEmail = askForEmail;
    }

    public Boolean getAskForMobilePhone() {
        return askForMobilePhone;
    }

    public void setAskForMobilePhone(Boolean askForMobilePhone) {
        this.askForMobilePhone = askForMobilePhone;
    }

    public Boolean getRequireEscort() {
        return requireEscort;
    }

    public void setRequireEscort(Boolean requireEscort) {
        this.requireEscort = requireEscort;
    }

    public String getSignatureSetting() {
        return signatureSetting;
    }

    public void setSignatureSetting(String signatureSetting) {
        this.signatureSetting = signatureSetting;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
