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

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.model.DeleteResponse;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.example.peaoplepassclientside.visitorModel.VisitTo;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReceiverFragment extends Fragment  {


    public ReceiverFragment() {
        // Required empty public constructor
    }
    NavController navController;
    Item item;
    Button refactor;
    String id;
    TextView txtOrdered, txtVisitor, txtVisitorString, txtReceiver,txtReceiverString,
        txtDate, txtDateString, txtPhone, txtPhoneString,txtMail, txtMailString,txtEscort,txtEscortString,
        txtChange;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            item = (Item) getArguments().getSerializable("data");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_receiver, container, false);
        navController = Navigation.findNavController(getActivity(),R.id.frame_main);
        refactor = view.findViewById(R.id.go_to_first);
        if (item!=null){
            id = item.getId();
        }
        txtOrdered = view.findViewById(R.id.ordered_txt);
        txtVisitor = view.findViewById(R.id.txt_visitor_upcoming_order);
        txtVisitorString = view.findViewById(R.id.string_visitor_order);
        txtDate = view.findViewById(R.id.txtDateOrder);
        txtDateString = view.findViewById(R.id.date_string_order);
        txtReceiver = view.findViewById(R.id.txtReceiverUpcoming_order);
        txtReceiverString = view.findViewById(R.id.string_receiver_order);
        txtMailString = view.findViewById(R.id.mail_order_string);
        txtMail = view.findViewById(R.id.mail_order_txt);
        txtPhoneString = view.findViewById(R.id.phone_order_string);
        txtPhone = view.findViewById(R.id.phone_order_txt);
        txtEscort = view.findViewById(R.id.escort_txt_order);
        txtEscortString =  view.findViewById(R.id.escort_string_order);
        txtChange = view.findViewById(R.id.change_txt);

        txtVisitor.setText(item.getVisitorSurname()+" "+item.getVisitorName()+" "+item.getVisitorMiddlename());
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
        VisitTo  visitTo = item.getVisitTo();
        if (visitTo!=null){
            txtReceiver.setText(visitTo.getName());
        }
        if (item.getRequireEscort()){
            txtEscort.setText("c сопровождения");
        }else {
            txtEscort.setText("без сопровождения");
        }
        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteOrder();
            }
        });

        refactor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("id",id);
                navController.popBackStack();
                navController.navigate(R.id.changeOrderFragment,bundle);
            }
        });

        return view;
    }

    private void deleteOrder() {
        String id = item.getId();
        ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
        Call<DeleteResponse> call = apiInterFace.deleteOrder(Constants.token,id);
        call.enqueue(new Callback<DeleteResponse>() {
            @Override
            public void onResponse(Call<DeleteResponse> call, Response<DeleteResponse> response) {
                if (response.isSuccessful()){
                    getActivity().onBackPressed();
                }else {
                    Log.d("tagged", "onResponse: "+response.body());
                }
            }

            @Override
            public void onFailure(Call<DeleteResponse> call, Throwable t) {

            }
        });
    }


}
