package com.example.peaoplepassclientside.clallbacks;

import com.example.peaoplepassclientside.model.ResponseEmploye;
import com.example.peaoplepassclientside.visitorModel.Item;

public interface AdapterCallBack {
    void getItem(Item item);
    void returnEmploye(ResponseEmploye.EmployeList employeList);
}
