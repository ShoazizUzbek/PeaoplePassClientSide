package com.example.peaoplepassclientside.views;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.FragmentClicks;
import com.example.peaoplepassclientside.model.PhoneCall;
import com.example.peaoplepassclientside.model.PhoneVerification;
import com.example.peaoplepassclientside.model.UserRegistration;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;
import com.goodiebag.pinview.Pinview;

import java.io.IOException;
import java.security.MessageDigest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificationFragment extends Fragment {


    public VerificationFragment() {
        // Required empty public constructor
    }
    Button button;
    FragmentClicks fragmentClicks;
    TextView textView,txtDidnotgetcode, txtGotoBack, txtTimer;
    TextView chronometer;
    Pinview pinview;
    private CountDownTimer mCountDownTimer;
    private long mTimeLeftInMillis = 30000;
    String phoneNumber;
    ProgressDialog progressDialog;

    SharedPreferences preferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.get_verification_code, container, false);

        if (getArguments()!=null){
            phoneNumber = getArguments().getString("phoneNumber");
        }
        progressDialog = new ProgressDialog(getContext());
        pinview = new Pinview(getContext());
        textView = v.findViewById(R.id.enter_ver_code);
        pinview = v.findViewById(R.id.pinview);
        txtDidnotgetcode = v.findViewById(R.id.didnot_get_code);
        txtGotoBack = v.findViewById(R.id.go_to_back);
        chronometer = v.findViewById(R.id.timer_txt_show);
        txtTimer = v.findViewById(R.id.timer_txt);
        Font.montSerrat(getContext(),textView);
        Font.montSerrat(getContext(),pinview);
        Font.montSerrat(getContext(),txtDidnotgetcode);
        Font.montSerrat(getContext(),txtGotoBack);
        startTimer(mTimeLeftInMillis);
        updateCountDownText();
        txtDidnotgetcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reSendSms();
                startTimer(30000);
            }
        });
        txtGotoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentClicks.goToEnterNumberFragment();
            }
        });
        pinview.setPinViewEventListener(new Pinview.PinViewEventListener() {
            @Override
            public void onDataEntered(Pinview pinview, boolean fromUser) {
                String pindata = pinview.getValue();
                Log.d("pindata", "onDataEntered: "+pindata);
                    goToHomePage(pindata);

            }
        });

        preferences = getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);

        return v;
    }

    private void goToHomePage(String pinData) {
        String code = pinData+"_"+phoneNumber;
        String hashshingValue = md5(code).toUpperCase();
        Log.d("tagged", "goToHomePage: hash "+hashshingValue);
        Log.d("tagged", "goToHomePage: pincode "+hashshingValue);
        Log.d("tagged", "goToHomePage: "+Constants.hashCode);

        if (hashshingValue.equals(Constants.hashCode)){
            if (Constants.userCheck){
                Intent intent = new Intent(getContext(),ProfileFillActivity.class);
                intent.putExtra("phone",phoneNumber);
                intent.putExtra("code",pinData);
                startActivity(intent);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getActivity().finish();

            }else {
                progressDialog.setMessage("Загрузка...");
                progressDialog.show();
                goToHomeActivity(pinData,phoneNumber);



            }
        }else {

        }


    }

    private void goToHomeActivity(String pinData, String phoneNumber) {
        ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
        UserRegistration phoneCall = new UserRegistration();
        phoneCall.setPhone(phoneNumber);
        phoneCall.setHash(pinData);
        Call<ResponseBody> call = apiInterFace.registerUser(phoneCall);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String token = response.body().string();
                        Constants.token = token;
                        preferences.edit().putString("phone",Constants.phoneNumber).apply();
                        preferences.edit().putString("hash",Constants.hashCode).apply();

                        Intent intent = new Intent(getContext(), HomeActivity.class);
                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        getActivity().finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.d("tagged", "onResponse: "+ response.isSuccessful()+" "+response.body());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tagged", "onFailure: "+t.getMessage());
            }
        });
    }

    public  String md5( String toEncrypt) {
        try {
            final MessageDigest digest = MessageDigest.getInstance("md5");
            digest.update(toEncrypt.getBytes());
            final byte[] bytes = digest.digest();
            final StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(String.format("%02X", bytes[i]));
            }
            return sb.toString().toUpperCase();
        } catch (Exception exc) {
            return ""; // Impossibru!
        }
    }

    private void reSendSms() {
        ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
        PhoneCall phoneCall = new PhoneCall();
        phoneCall.setPhone(phoneNumber);
        Call<PhoneVerification> call = apiInterFace.sendSms(phoneCall);
        call.enqueue(new Callback<PhoneVerification>() {
            @Override
            public void onResponse(Call<PhoneVerification> call, Response<PhoneVerification> response) {
                PhoneVerification phoneVerification = response.body();
                if (phoneVerification!=null){
                    Log.d("tagged", "onResponse: "+phoneVerification.getCodeHash()+" "+phoneVerification.getShowAddProfile());

                }
            }

            @Override
            public void onFailure(Call<PhoneVerification> call, Throwable t) {
                Log.d("tagged", "onFailure: fail"+t.getMessage());
            }
        });
    }

    private void startTimer(long timer) {
        mCountDownTimer = new CountDownTimer(timer, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                txtDidnotgetcode.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    void  updateCountDownText(){
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;
        if (seconds == 1){
            seconds = 0;
        }
        String timeLeftFormatted = String.valueOf(  seconds);

        chronometer.setText(timeLeftFormatted);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof FragmentClicks)
            fragmentClicks = (FragmentClicks) getActivity();
    }
}
