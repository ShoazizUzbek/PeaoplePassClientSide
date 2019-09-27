package com.example.peaoplepassclientside.views;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.HomePageCallBacks;
import com.example.peaoplepassclientside.model.AddVisitResponse;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.example.peaoplepassclientside.visitorModel.VisitTo;

/**
 * A simple {@link Fragment} subclass.
 */
public class AboutPassFragment extends Fragment {


    public AboutPassFragment() {
        // Required empty public constructor
    }
    NavController navController;
    AddVisitResponse item;
    Button refactor;
    HomePageCallBacks fragmentClicks;
    String id;
    TextView txtOrdered, txtVisitor, txtVisitorString, txtReceiver,txtReceiverString,
            txtDate, txtDateString, txtPhone, txtPhoneString,txtMail, txtMailString,txtEscort,txtEscortString,
            txtChange;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof HomePageCallBacks)
            fragmentClicks = (HomePageCallBacks) getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            item = (AddVisitResponse) getArguments().getSerializable("visit");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_pass, container, false);
        navController = Navigation.findNavController(getActivity(),R.id.frame_main);
        refactor = view.findViewById(R.id.go_to_first_c);
        if (item.getId()!=null){
            id = item.getId();
        }
        txtOrdered = view.findViewById(R.id.ordered_txt_c);
        txtVisitor = view.findViewById(R.id.txt_visitor_upcoming_order_c);
        txtVisitorString = view.findViewById(R.id.string_visitor_order_c);
        txtDate = view.findViewById(R.id.txtDateOrder_c);
        txtDateString = view.findViewById(R.id.date_string_order_c);
        txtReceiver = view.findViewById(R.id.txtReceiverUpcoming_order_c);
        txtReceiverString = view.findViewById(R.id.string_receiver_order_c);
        txtMailString = view.findViewById(R.id.mail_order_string_c);
        txtMail = view.findViewById(R.id.mail_order_txt_c);
        txtPhoneString = view.findViewById(R.id.phone_order_string_c);
        txtPhone = view.findViewById(R.id.phone_order_txt_c);
        txtEscort = view.findViewById(R.id.escort_txt_order_c);
        txtEscortString =  view.findViewById(R.id.escort_string_order_c);
        txtChange = view.findViewById(R.id.change_txt_c);

        txtVisitor.setText(item.getVisitorSurname()+" "+item.getVisitorName()+" "+item.getVisitorMiddlename());
        Log.d("phonenum", "onCreateView: "+item.getVisitorPhone());
        txtPhone.setText(item.getVisitorPhone());
        txtMail.setText(item.getVisitorEmail());
        Font.montSerrat(getContext(),txtOrdered);
        Font.montSerrat(getContext(),txtVisitor);
        Font.montSerrat(getContext(),txtVisitorString);
        Font.montSerrat(getContext(),txtDate);
        Font.montSerrat(getContext(),txtDateString);
        Font.montSerrat(getContext(),txtReceiver);
        Font.montSerrat(getContext(),txtReceiverString);
        Font.montSerrat(getContext(),txtMail);
        Font.montSerrat(getContext(),txtMailString);
        Font.montSerrat(getContext(),txtPhone);
        Font.montSerrat(getContext(),txtEscortString);
        Font.montSerrat(getContext(),txtEscort);
        Font.montSerrat(getContext(),txtChange);
        VisitTo visitTo = item.getVisitTo();
        if (visitTo!=null){
            txtReceiver.setText(visitTo.getName());
        }
        if (item.getRequireEscort()){
            txtEscort.setText("требуется");
        }else {
            txtEscort.setText("не требуется");
        }
        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                navController.popBackStack();
                navController.navigate(R.id.changeOrderFragment,bundle);
            }
        });

        refactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentClicks.goToHomePage();
            }
        });

        return view;
    }

}
