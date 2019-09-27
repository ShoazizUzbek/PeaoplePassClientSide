package com.example.peaoplepassclientside.views;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.apache.commons.io.FileUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.CheckStatus;
import com.example.peaoplepassclientside.model.PhotoResult;
import com.example.peaoplepassclientside.model.UserRegistration;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static java.security.AccessController.getContext;

public class UpLoadImageActivity extends AppCompatActivity {

    CircleImageView circleImageView;
    Uri imageUri;
    Button button;
    ProgressDialog progressDialog;
    CheckStatus checkStatus;
    TextView textView;
    String name, surname, email, middleName, phone, code;
    UUID photoId;
    SharedPreferences preferences;
    InputStream imageStream;
    private static final String[] LOCAL_FILE_PROJECTION = { "_data" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up_load_image);

        if (getIntent()!=null){
            name = getIntent().getStringExtra("name");
            surname = getIntent().getStringExtra("surname");
            email = getIntent().getStringExtra("email");
            middleName = getIntent().getStringExtra("middlename");
            phone = getIntent().getStringExtra("phone");
            code = getIntent().getStringExtra("code");
        }
        textView = findViewById(R.id.select_pic);
        button = findViewById(R.id.save_photo_btn);
        Font.montSerrat(this,textView);
        Font.montSerrat(this,button);
        progressDialog = new ProgressDialog(this);
        circleImageView = findViewById(R.id.profile_img_fill_registration);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 100);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Загрузка");
                progressDialog.show();
                if (imageUri.toString()!=null&&!imageUri.toString().equals("")){
                    uploadImg(new CheckStatus() {
                        @Override
                        public void checkUser(boolean check) {
                            if (check){
                                updateUser();
                            }

                        }
                    });
                }else {
                    progressDialog.dismiss();
                }

            }
        });

         preferences = getSharedPreferences("user", MODE_PRIVATE);
    }

    private void updateUser() {

        ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
        final UserRegistration phoneCall = new UserRegistration();
        phoneCall.setName(name);
        phoneCall.setSurname(surname);
        phoneCall.setEmail(email);
        phoneCall.setMiddlename(middleName);
        phoneCall.setPhone(phone);
        phoneCall.setHash(code);
        phoneCall.setPhoto(photoId);
        Call<ResponseBody> call = apiInterFace.registerUser(phoneCall);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    try {
                        String token = response.body().string();
                        Constants.token = token;
                        Intent intent = new Intent(UpLoadImageActivity.this, HomeActivity.class);
                        startActivity(intent);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        finish();
                        preferences.edit().putString("phone",Constants.phoneNumber).apply();
                        preferences.edit().putString("hash",Constants.hashCode).apply();
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
                imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                circleImageView.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
    private void uploadImg(final CheckStatus checkStatus) {
        ApiInterFace apiClient = ApiClient.apiRequest().create(ApiInterFace.class);

        String selectedImagePath = null;
        Uri selectedImageUri = imageUri;


        if (Build.VERSION.SDK_INT > 19) {
            // Call some material design APIs here
            String id = DocumentsContract.getDocumentId(imageUri);
            File file = new File(getCacheDir().getAbsolutePath()+"/"+id);
            writeFile(imageStream, file);
            selectedImagePath = file.getAbsolutePath();

        } else {
            Cursor cursor = null;
            if (imageUri!=null){
                cursor = getContentResolver().query(
                        imageUri, LOCAL_FILE_PROJECTION, null, null, null);
            }
            if (cursor == null) {
                selectedImagePath = imageUri.getPath();
            } else {
                if(cursor.moveToFirst())
                {
                    int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    selectedImagePath = cursor.getString(idx);
                }
                cursor.close();
            }
        }
        File originalFile = FileUtils.getFile(selectedImagePath);

        RequestBody filePart = RequestBody.create(
                MediaType.parse(getContentResolver().getType(selectedImageUri)),
                originalFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", originalFile.getName(), filePart);



        Call<PhotoResult> call  = apiClient.uploadPhoto(Constants.token,part);
        call.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                PhotoResult photoResult = response.body();
                try {
                    if (photoResult!=null){
                        photoId = photoResult.getFileID();
                        checkStatus.checkUser(true);
                        Log.d("tagged", "onResponse: "+photoResult.getFileID()+" ms ");


                    }else {
                        progressDialog.dismiss();
                        Log.d("tagged", "onResponse: "+response.body());
                    }
                }catch (Exception e){

                }


            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("tagged", "onFailure: "+t.getMessage());
            }
        });

    }
    void writeFile(InputStream in, File file) {
        OutputStream out = null;
        try {
            out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while((len=in.read(buf))>0){
                out.write(buf,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                if ( out != null ) {
                    out.close();
                }
                in.close();
            } catch ( IOException e ) {
                e.printStackTrace();
            }
        }
    }

}
