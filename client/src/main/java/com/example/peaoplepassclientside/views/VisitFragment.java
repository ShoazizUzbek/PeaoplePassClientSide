package com.example.peaoplepassclientside.views;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.example.peaoplepassclientside.visitorModel.VisitTo;
import com.example.peaoplepassclientside.visitorModel.Visitor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class VisitFragment extends Fragment {


    public VisitFragment() {
        // Required empty public constructor
    }
    Item item;
    Visitor visitor;
    String phone, mail, name,photo, pdf;
    ConstraintLayout constraintLayout;
    Toolbar toolbar;
    ImageView imageView;
    TextView txtToolbar,txtStatus,txtStringVisitor,txtVisitor,txtStringReceiver,txtReceiver,txtStringDate,txtDate,txtStringTimeEnter,txtTimeEnter,
    txtStringTimeExit,txtTimeExit,txtStringEscort,txtEscort;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!=null){
            item = (Item)bundle.get("data");
            Log.d("atgged", "onCreate: "+item.getVisitDate());
        }
    }

    NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_visit, container, false);
        toolbar = v.findViewById(R.id.toolbar_visit);
        imageView = v.findViewById(R.id.back_visit);
        txtStatus = v.findViewById(R.id.status_visit);
        txtToolbar = v.findViewById(R.id.txt_visit_toolbar);
        txtStringVisitor = v.findViewById(R.id.string_visitor_visit);
        txtVisitor = v.findViewById(R.id.txt_visitor_upcoming_visit);
        txtStringReceiver = v.findViewById(R.id.string_receiver_visit);
        txtReceiver = v.findViewById(R.id.txtReceiverUpcoming_visit);

        txtStringDate  =v.findViewById(R.id.date_string_visit);
        txtDate = v.findViewById(R.id.txtDateVisit);
        txtStringTimeEnter = v.findViewById(R.id.time_enter_visit);
        txtTimeEnter = v.findViewById(R.id.time_enter_visit_txt);
        txtStringTimeExit = v.findViewById(R.id.time_exit_visit);
        txtTimeExit = v.findViewById(R.id.time_exit_visit_txt);
        txtStringEscort = v.findViewById(R.id.escort_string);
        txtEscort = v.findViewById(R.id.escort_txt);
        Font.montSerrat(getContext(),txtStatus);
        Font.montSerrat(getContext(),txtStringVisitor);
        Font.montSerrat(getContext(),txtVisitor);
        Font.montSerrat(getContext(),txtStringReceiver);
        Font.montSerrat(getContext(),txtReceiver);
        Font.montSerrat(getContext(),txtStringDate);
        Font.montSerrat(getContext(),txtDate);
        Font.montSerrat(getContext(),txtStringTimeEnter);
        Font.montSerrat(getContext(),txtTimeEnter);
        Font.montSerrat(getContext(),txtStringTimeExit);
        Font.montSerrat(getContext(),txtTimeExit);
        Font.montSerrat(getContext(),txtStringEscort);
        Font.montSerrat(getContext(),txtEscort);


        Font.montSerratMedium(getContext(),txtToolbar);
        if (item!=null){
            VisitTo receiver = item.getVisitTo();
            visitor = item.getVisitor();
            phone = item.getVisitorPhone();
            mail = item.getVisitorEmail();
            name = item.getVisitorName()+" \n"+item.getVisitorSurname()+" "+item.getVisitorMiddlename();
            if (visitor!=null){
                if (visitor.getPhoto()!=null){
                    photo = visitor.getPhoto();
                }
                if (visitor.getDocPhoto()!=null){
                    pdf = visitor.getDocPhoto();
                }
            }



            Date date = item.getVisitDate();
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            DateFormat timeFormat = new SimpleDateFormat("HH:mm");
            String time = timeFormat.format(date);
            String dateStrign = dateFormat.format(date);
            String state = item.getState();
            if (state == "COMPLETED"){
                txtStatus.setText("визит состоялся");
            }else if (state.equals("INCOMPLETED")){
                txtStatus.setText("визит не состоялся");
            }else {
                txtStatus.setText(state+" ");
            }
            txtVisitor.setText(item.getVisitorName()+" "+item.getVisitorSurname()+" "+item.getVisitorMiddlename());
            if (receiver!=null){
                txtReceiver.setText(receiver.getName()+" "+receiver.getSurname()+" "+receiver.getMiddlename());
            }
            if (date!=null){
                txtDate.setText(dateStrign);
                txtTimeEnter.setText(time+" ");
                txtTimeExit.setText(time+" ");
            }
            if (item.getRequireEscort()){
                txtEscort.setText("c сопровождения");
            }else {
                txtEscort.setText("без сопровождения");
            }

        }


        navController = Navigation.findNavController(getActivity(),R.id.frame_main);
        constraintLayout = v.findViewById(R.id.layout_second_visit);

        Log.d("visit page", "onCreateView: "+item.getVisitDate());
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("name",name);
                bundle.putString("phone",phone);
                bundle.putString("photo",photo);
                bundle.putString("mail",mail);
                bundle.putString("pdf",mail);
                navController.navigate(R.id.infoVisitorFragment,bundle);
            }
        });
    }
}
