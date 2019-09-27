package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

public class Employe {
    @SerializedName("page")
    int page;
    @SerializedName("perPage")
    int perPage;
    @SerializedName("sort")
    SortEmploye sort;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public SortEmploye getSort() {
        return sort;
    }

    public void setSort(SortEmploye sort) {
        this.sort = sort;
    }


}
