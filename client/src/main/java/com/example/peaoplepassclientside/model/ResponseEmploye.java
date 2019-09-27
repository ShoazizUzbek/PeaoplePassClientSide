package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseEmploye implements Serializable{
        @SerializedName("items")
        List<EmployeList> employeLists;

    public List<EmployeList> getEmployeLists() {
        return employeLists;
    }

    public void setEmployeLists(List<EmployeList> employeLists) {
        this.employeLists = employeLists;
    }

    public class EmployeList implements Serializable {
            String id;
            String name;
            String surname;
            String middlename;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSurname() {
            return surname;
        }

        public void setSurname(String surname) {
            this.surname = surname;
        }

        public String getMiddlename() {
            return middlename;
        }

        public void setMiddlename(String middlename) {
            this.middlename = middlename;
        }
    }

}
