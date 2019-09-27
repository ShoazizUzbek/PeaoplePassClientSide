package com.example.peaoplepassclientside.model;

import com.google.gson.annotations.SerializedName;

public class BodyVisitors {

    @SerializedName("page")
    private Integer page;
    @SerializedName("perPage")
    private Integer perPage;
    @SerializedName("sort")
    private Sort sort;
    @SerializedName("filters")
    private Filters filters;

    public BodyVisitors(Integer page, Integer perPage, Sort sort, Filters filters) {
        this.page = page;
        this.perPage = perPage;
        this.sort = sort;
        this.filters = filters;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Sort getSort() {
        return sort;
    }

    public void setSort(Sort sort) {
        this.sort = sort;
    }

    public Filters getFilters() {
        return filters;
    }

    public void setFilters(Filters filters) {
        this.filters = filters;
    }

}


