package com.example.peaoplepassclientside.views;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.adapter.ReceiverSelectAdapter;
import com.example.peaoplepassclientside.adapter.VisitorsAdapter;
import com.example.peaoplepassclientside.clallbacks.AdapterCallBack;
import com.example.peaoplepassclientside.clallbacks.FragmentClicks;
import com.example.peaoplepassclientside.clallbacks.HomePageCallBacks;
import com.example.peaoplepassclientside.model.AddVisitBody;
import com.example.peaoplepassclientside.model.AddVisitResponse;
import com.example.peaoplepassclientside.model.BodyVisitors;
import com.example.peaoplepassclientside.model.Employe;
import com.example.peaoplepassclientside.model.Filters;
import com.example.peaoplepassclientside.model.ResponseEmploye;
import com.example.peaoplepassclientside.model.SortEmploye;
import com.example.peaoplepassclientside.model.State;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;
import com.example.peaoplepassclientside.visitorModel.Item;
import com.example.peaoplepassclientside.visitorModel.VisitTo;
import com.example.peaoplepassclientside.visitorModel.VisitorsResult;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.textfield.TextInputEditText;
import com.suke.widget.SwitchButton;

import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderPass extends Fragment implements AdapterCallBack {


    public OrderPass() {
        // Required empty public constructor
    }


    ReceiverSelectAdapter receiverSelectAdapter;

    HomePageCallBacks homePageCallBacks;
    TextInputEditText edtSurname, edtName, edtMiddle, edtMail, edtPhone,edtDate;
    RelativeLayout relativeLayout;
    Button button;
    TextView whome,txtToolbar,txtData,txtDataRecov,txtEnterDate;
    ResponseEmploye.EmployeList employe;
    RecyclerView recyclerView;
    String idEmploye,nameEmploye, currentDate;
    ImageView img;
    ProgressDialog progressDialog;
    SwitchButton switchButton;
    boolean checked;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof HomePageCallBacks)
            homePageCallBacks = (HomePageCallBacks) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            employe = (ResponseEmploye.EmployeList)getArguments().getSerializable("employe");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_order_pass, container, false);
        switchButton = v.findViewById(R.id.switch_btn);
        checked = switchButton.isChecked();
        txtToolbar = v.findViewById(R.id.txt_order_toolbar);
        Font.montSerratMedium(getContext(),txtToolbar);
        txtData  = v.findViewById(R.id.txt_data_order);
        txtEnterDate = v.findViewById(R.id.enter_date);
        Font.montSerrat(getContext(),txtEnterDate);
        Font.montSerrat(getContext(),txtData);
        txtDataRecov = v.findViewById(R.id.txt_data_receiver);
        progressDialog = new ProgressDialog(getContext());
        img = v.findViewById(R.id.back_order);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        whome = v.findViewById(R.id.whome_txt);
        Font.montSerrat(getContext(),whome);
        recyclerView = v.findViewById(R.id.recycler_employe);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Date date  = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat sdfTxt = new SimpleDateFormat("yyyy-MM-dd");
        String txtDate  = sdfTxt.format(date);
         currentDate = sdf.format(date);
        txtEnterDate.setText(txtDate);
       loadReceivers();
       relativeLayout = v.findViewById(R.id.edt__order_receiver);
        edtSurname = v.findViewById(R.id.edtOrderSurName);
        edtName = v.findViewById(R.id.edtOrderName);
        edtMiddle = v.findViewById(R.id.edtOrderMiddleName);
        edtMail = v.findViewById(R.id.edtOrderMail);
        edtPhone  = v.findViewById(R.id.edtOrderPhone);
        Font.montSerrat(getContext(),edtName);
        Font.montSerrat(getContext(),edtSurname);
        Font.montSerrat(getContext(),edtMiddle);
        Font.montSerrat(getContext(),edtMail);
        Font.montSerrat(getContext(),edtPhone);
        button = v.findViewById(R.id.do_pass_btn);
        Font.montSerrat(getContext(),button);
        
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView.getVisibility()==View.GONE){
                    recyclerView.setVisibility(View.VISIBLE);
                }else {
                    recyclerView.setVisibility(View.GONE);

                }
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Загрузка");
                progressDialog.show();
                addVisit();
            }
        });



        return v;
    }

    private void loadReceivers() {
        final ProgressDialog  progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Загрузка...");
        progressDialog.show();
        ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
        Employe employe = new Employe();
        employe.setPage(1);
        employe.setPerPage(35);
        SortEmploye sortEmploye = new SortEmploye();
        sortEmploye.setSurname("DESC");
        employe.setSort(sortEmploye);

        Call<ResponseEmploye> call = apiInterFace.getEmploye(Constants.token, employe);
        call.enqueue(new Callback<ResponseEmploye>() {
            @Override
            public void onResponse(Call<ResponseEmploye> call, Response<ResponseEmploye> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    List<ResponseEmploye.EmployeList> employeLists = response.body().getEmployeLists();
                    receiverSelectAdapter = new ReceiverSelectAdapter(employeLists, getContext());
                    receiverSelectAdapter.setAdapterCallBack(OrderPass.this);
                    recyclerView.setAdapter(receiverSelectAdapter);
                }
            }

            @Override
            public void onFailure(Call<ResponseEmploye> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка с интернетом", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
    }
    private void addVisit(){
        String surname  = edtSurname.getText().toString();
        String name  = edtName.getText().toString();
        String middle  = edtMiddle.getText().toString();
        String phone  = edtPhone.getText().toString();
        String mail  = edtMail.getText().toString();
//        String date = edtDate.getText().toString();




        AddVisitBody addVisitBody = new AddVisitBody();
        addVisitBody.setVisitorName(name);
        addVisitBody.setVisitorSurname(surname);
        addVisitBody.setVisitorMiddlename(middle);
        addVisitBody.setVisitorEmail(mail);
        addVisitBody.setVisitorPhone(phone);
        addVisitBody.setRequireEscort(checked);
        addVisitBody.setVisitDate(currentDate);
        Log.d("atgged", "addVisit: "+name+" "+idEmploye+" id");
        VisitTo visitTo = new VisitTo();
        visitTo.setId(idEmploye);
        addVisitBody.setVisitTo((visitTo));
        ApiInterFace apiInterface = ApiClient.apiRequest().create(ApiInterFace.class);
        Call<AddVisitResponse> call = apiInterface.addVisit(Constants.token,addVisitBody);
        call.enqueue(new Callback<AddVisitResponse>() {
            @Override
            public void onResponse(Call<AddVisitResponse> call, Response<AddVisitResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    AddVisitResponse addVisitResponse = response.body();
                    homePageCallBacks.goToVisitFinish(addVisitResponse);
                }else {
                    Log.d("tagged", "onResponse: "+response.isSuccessful());
                }

            }

            @Override
            public void onFailure(Call<AddVisitResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Ошибка с интернетом", Toast.LENGTH_SHORT).show();

                progressDialog.dismiss();
                Log.d("add", "onFailure: ");
            }
        });

    }

    @Override
    public void getItem(Item item) {

    }

    @Override
    public void returnEmploye(ResponseEmploye.EmployeList employeList) {
        nameEmploye = employeList.getSurname()+" "+employeList.getName()+" "+employeList.getMiddlename();
        idEmploye = employeList.getId();
        Log.d("tagged", "returnEmploye: "+idEmploye);
        whome.setTextColor(getResources().getColor(R.color.textColor));
        whome.setText(employeList.getName()+" "+employeList.getSurname()+" "+employeList.getMiddlename());
        recyclerView.setVisibility(View.GONE);
    }
}
