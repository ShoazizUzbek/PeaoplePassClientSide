package com.example.peaoplepassclientside.clallbacks;

import com.example.peaoplepassclientside.model.AddVisitResponse;
import com.example.peaoplepassclientside.visitorModel.Item;

public interface HomePageCallBacks {
     void gotToFilterFragment(String choose);
     void goToVisit(Item item);
     void goToHomePage();
     void goToVisitFinish(AddVisitResponse addVisitResponse);


}
