package com.example.peaoplepassclientside.model;

import java.util.UUID;

public class PhotoResult {
    UUID fileID;
    int code;
    String message;

    public UUID getFileID() {
        return fileID;
    }

    public void setFileID(UUID fileID) {
        this.fileID = fileID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
