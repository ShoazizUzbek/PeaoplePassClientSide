package com.example.peaoplepassclientside.views;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.FragmentClicks;
import com.example.peaoplepassclientside.model.PhoneCall;
import com.example.peaoplepassclientside.model.PhoneVerification;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class EnterNumberFragment extends Fragment {


    public EnterNumberFragment() {
        // Required empty public constructor
    }
    Button button;
    TextView txtEnterPhone;
    EditText edtEnterNumber;
    FragmentClicks fragmentClicks;
    String phoneNumber;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof FragmentClicks)
            fragmentClicks = (FragmentClicks) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_enter_number, container, false);
        button = v.findViewById(R.id.enter_number_next_btn);
        txtEnterPhone = v.findViewById(R.id.enter_phone_num_txt);
        edtEnterNumber = v.findViewById(R.id.enter_number_edt);
        Font.montSerrat(getContext(),txtEnterPhone);
        Font.montSerrat(getContext(),edtEnterNumber);
        Font.montSerrat(getContext(),button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentClicks!=null){
                    sendToGetCode();
                }
            }
        });

        return v;
    }

    private void sendToGetCode() {
        phoneNumber = edtEnterNumber.getText().toString();
        if (phoneNumber==null||phoneNumber.equals("")||phoneNumber.isEmpty()){
            edtEnterNumber.setError("Номер телефон");
        }else {
            char firstSign = phoneNumber.charAt(0);
            if (firstSign != '+'){
                phoneNumber = "+"+phoneNumber;
                Log.d("tagged", "sendToGetCode: "+phoneNumber);

            }
            ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
            PhoneCall phoneCall = new PhoneCall();
            phoneCall.setPhone(phoneNumber);
            Call<PhoneVerification> call = apiInterFace.sendSms(phoneCall);
            call.enqueue(new Callback<PhoneVerification>() {
                @Override
                public void onResponse(Call<PhoneVerification> call, Response<PhoneVerification> response) {
                    PhoneVerification phoneVerification = response.body();
                    if (phoneVerification!=null){
                        Constants.phoneNumber = phoneNumber;
                        Constants.hashCode = phoneVerification.getCodeHash();
                        Constants.userCheck = phoneVerification.getShowAddProfile();
                        fragmentClicks.goToVerificationFragment(phoneNumber);
                        Log.d("tagged enter number", "onResponse: "+phoneVerification.getCodeHash()+" "+phoneVerification.getShowAddProfile());

                    }
                }

                @Override
                public void onFailure(Call<PhoneVerification> call, Throwable t) {
                    Log.d("tagged", "onFailure: fail"+t.getMessage());
                }
            });
        }


    }

}
