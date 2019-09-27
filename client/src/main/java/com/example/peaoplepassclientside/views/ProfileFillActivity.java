package com.example.peaoplepassclientside.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.model.PhoneCall;
import com.example.peaoplepassclientside.model.PhoneVerification;
import com.example.peaoplepassclientside.model.PhotoResult;
import com.example.peaoplepassclientside.model.UserRegistration;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFillActivity extends AppCompatActivity {


    CircleImageView imageView;
    Button register;
    Uri imageUri;
    TextView textView,uploadPhoto;
    EditText edtName,edtSurname, edtMiddlename,edtEmail;
    ProgressDialog progressDialog;
    String phone, code;
    boolean check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_fill);
        register = findViewById(R.id.register_fill_btn);
        textView = findViewById(R.id.txt_toolbar_fill);
        edtName = findViewById(R.id.name_fill);
        edtSurname = findViewById(R.id.surname_fill);
        edtMiddlename = findViewById(R.id.middlename_fill);
        edtEmail = findViewById(R.id.email_fill);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Загрузка...");
        Font.montSerrat(this,textView);
        Font.montSerrat(this,register);
        Font.montSerrat(this,edtName);
        Font.montSerrat(this,edtSurname);
        Font.montSerrat(this,edtMiddlename);
        Font.montSerrat(this,edtEmail);
        if (getIntent()!=null){
             phone = getIntent().getStringExtra("phone");
             code = getIntent().getStringExtra("code");
            
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration();
            }
        });
    }

    private void registration() {
        final String name = edtName.getText().toString();
        final String surname = edtSurname.getText().toString();
        final String middle =  edtMiddlename.getText().toString();
        final String email = edtEmail.getText().toString();
        if (name.isEmpty()||name.equals("")){
            edtName.setError(getResources().getString(R.string.fill));
            return;
        }
        if (surname.isEmpty() || surname.equals("")){
            edtSurname.setError(getResources().getString(R.string.fill));
            return;
        }
        if (middle.isEmpty()||middle.equals("")){
            edtMiddlename.setError(getResources().getString(R.string.fill));
            return;
        }
        if (email.isEmpty()||email.equals("")){
            edtEmail.setError(getResources().getString(R.string.fill));
            return;
        }
        Log.d("tagged", "registration: phone "+phone+" code "+code);
        progressDialog.show();
        ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
        UserRegistration phoneCall = new UserRegistration();
        phoneCall.setName(name);
        phoneCall.setSurname(surname);
        phoneCall.setEmail(email);
        phoneCall.setMiddlename(middle);
        phoneCall.setPhone(phone);
        phoneCall.setHash(code);
        Call<ResponseBody> call = apiInterFace.registerUser(phoneCall);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String token = response.body().string();
                        Constants.token = token;
                        Intent intent = new Intent(ProfileFillActivity.this,UpLoadImageActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("surname",surname);
                        intent.putExtra("email",email);
                        intent.putExtra("middlename",middle);
                        intent.putExtra("phone",phone);
                        intent.putExtra("code",code);

                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
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




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode==RESULT_OK&& data !=null){
            try {
                Log.d("tagged", "onActivityResult: ");
                 imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
