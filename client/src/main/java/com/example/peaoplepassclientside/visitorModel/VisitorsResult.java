package com.example.peaoplepassclientside.visitorModel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class VisitorsResult {
    @SerializedName("items")
    private List<Item> items = null;
    @SerializedName("total")
    private Integer total;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}

