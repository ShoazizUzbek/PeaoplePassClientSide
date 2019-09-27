package com.example.peaoplepassclientside.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.model.User;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText edtLogin, edtPassword;
    Button btnLogin;
    TextView errorTxt;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.item_back_gr_white));
        }
        errorTxt = findViewById(R.id.txtError);
        edtLogin        = findViewById(R.id.edt_login);
        edtPassword     = findViewById(R.id.edt_password);
        btnLogin        = findViewById(R.id.registration_btn);
        progressDialog = new ProgressDialog(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClick();

            }
        });
    }
    private void buttonClick() {

        String login        = edtLogin.getText().toString();
        String password     = edtPassword.getText().toString();
        String errorEmptyLogin = getResources().getString(R.string.error_empty_text);
        if (login.isEmpty() || login.equals("")){
            edtLogin.setError(errorEmptyLogin);
            return;
        }
        if (password.isEmpty()|| password.equals("")){
            edtPassword.setError(errorEmptyLogin);
            return;
        }
        progressDialog.setMessage(getString((R.string.loading)));
        progressDialog.show();
        ApiInterFace apiInterface = ApiClient.apiRequest().create(ApiInterFace.class);
        User newUser = new User(login,password);

        Call<ResponseBody> call = apiInterface.loginUser(newUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    String res = response.body().string();
                    Log.d("tagged", "check: "+res);
                    if (!res.isEmpty() && !res.equals(null)){
                        Log.d("tagged", "onResponse: inside ");

                        Constants.token = res;
                        Intent intent = new Intent(LoginActivity.this,HomeActivity.class );
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(LoginActivity.this, "Ошибка", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    errorTxt.setVisibility(View.VISIBLE);
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Log.d("tagged", "onFailure: "+t.getMessage());
                errorTxt.setText("Network failed");
                errorTxt.setVisibility(View.VISIBLE);
            }
        });

    }

}
