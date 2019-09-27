package com.example.peaoplepassclientside.views;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;
import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.JWTUtils;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.clallbacks.CheckStatus;
import com.example.peaoplepassclientside.model.PhotoResult;
import com.example.peaoplepassclientside.model.UserRegistration;
import com.example.peaoplepassclientside.model.UserUpdate;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;
import com.google.android.material.textfield.TextInputEditText;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }
    ProgressDialog progressDialog;
    CircleImageView circleImageView;
    Uri imageUri;
    Button saveBtn;
    UUID photoId;
    TextView toolbarTxt;
    TextInputEditText edtName, edtSurname, edtMiddle, edtMail;
    String name=  "",surname="",middle = "",mail = "";
    InputStream imageStream;
    private static final String[] LOCAL_FILE_PROJECTION = { "_data" };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);
        progressDialog = new ProgressDialog(getContext());
        toolbarTxt = view.findViewById(R.id.txt_toolbar_profile);
        circleImageView = view.findViewById(R.id.profile_img_profile_fragment);
        saveBtn = view.findViewById(R.id.btn_save);
        edtName = view.findViewById(R.id.edtProfileName);
        edtSurname = view.findViewById(R.id.edtProfileSurname);
        edtMiddle = view.findViewById(R.id.edtProfileMiddleName);
        edtMail = view.findViewById(R.id.edtProfileMail);
        Font.montSerratMedium(getContext(),toolbarTxt);
        Font.montSerrat(getContext(),saveBtn);
        Font.montSerrat(getContext(),edtName);
        Font.montSerrat(getContext(),edtMiddle);
        Font.montSerrat(getContext(),edtSurname);
        Font.montSerrat(getContext(),edtMail);
        Font.montSerrat(getContext(),saveBtn);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPermissionREAD_EXTERNAL_STORAGE(getContext())) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(intent, 100);
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("Загрузка...");
                progressDialog.show();
                upDatePhoto(new CheckStatus() {
                    @Override
                    public void checkUser(boolean check) throws JSONException {
                        if (check){
                            updateUser();
                        }
                    }
                });
            }
        });
    }

    public  boolean checkPermissionREAD_EXTERNAL_STORAGE(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {


                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    100);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }
    private void updateUser() throws JSONException {

        if (!edtName.getText().toString().isEmpty()){
            name = edtName.getText().toString();
        }
        if (!edtSurname.getText().toString().isEmpty()){
            surname = edtSurname.getText().toString();

        }

        if (!edtMiddle.getText().toString().isEmpty()){
            middle = edtMiddle.getText().toString();
        }

        if (!edtMail.getText().toString().isEmpty()){
            mail = edtMail.getText().toString();
        }
        ApiInterFace apiInterFace = ApiClient.apiRequest().create(ApiInterFace.class);
        UserUpdate phoneCall = new UserUpdate();

        try {
            JWTUtils.decoded(Constants.token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject json = null;
        try {
            json = new JSONObject(Constants.decodedToke);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String id = json.getJSONObject("user").getString("id");
        Log.d("tagged", "updateUser: "+id);
        phoneCall.setId(id);
        phoneCall.setName(name);
        phoneCall.setSurname(surname);
        phoneCall.setEmail(mail);
        phoneCall.setMiddlename(middle);
        phoneCall.setPhoto(photoId);
        Call<UserUpdate> call = apiInterFace.updateUser(Constants.token,phoneCall);
        call.enqueue(new Callback<UserUpdate>() {
            @Override
            public void onResponse(Call<UserUpdate> call, Response<UserUpdate> response) {
                progressDialog.dismiss();
                UserUpdate userUpdate = response.body();
                if (response.isSuccessful()){
                    if (userUpdate!=null){
                        saveBtn.setBackgroundColor(getResources().getColor(R.color.btn_back));
                        saveBtn.setTextColor(getResources().getColor(R.color.txt_btn));
                        saveBtn.setText("изменения сохранены");
                        edtName.setText("");
                        edtSurname.setText("");
                        edtMail.setText("");
                        edtMiddle.setText("");
                    }
                }
               else {
                    //Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<UserUpdate> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(getContext(), "Ошибка с интернетом", Toast.LENGTH_SHORT).show();
                Log.d("tagged profile", "onFailure: ");
            }
        });

    }


    private void upDatePhoto(final CheckStatus checkStatus) {

        ApiInterFace apiClient = ApiClient.apiRequest().create(ApiInterFace.class);
//        Uri uri  = imageUri;
        String selectedImagePath = null;


        if (Build.VERSION.SDK_INT > 19) {
            // Call some material design APIs here
            String id = DocumentsContract.getDocumentId(imageUri);
            File file = new File(getContext().getCacheDir().getAbsolutePath()+"/"+id);
            writeFile(imageStream, file);
            selectedImagePath = file.getAbsolutePath();

        } else {
            Cursor cursor = null;
            if (imageUri!=null){
                cursor = getContext().getContentResolver().query(
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
                MediaType.parse(getContext().getContentResolver().getType(imageUri)),
                originalFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", originalFile.getName(), filePart);
        Call<PhotoResult> call  = apiClient.uploadPhoto(Constants.token,part);
        call.enqueue(new Callback<PhotoResult>() {
            @Override
            public void onResponse(Call<PhotoResult> call, Response<PhotoResult> response) {
                Toast.makeText(getContext(), "null", Toast.LENGTH_SHORT).show();
                Log.d("tagged", "onResponse: "+response.isSuccessful());
                PhotoResult photoResult = response.body();
                try {
                    checkStatus.checkUser(true);
                    if (photoResult!=null){
                        photoId = photoResult.getFileID();
                        Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
                        Log.d("photoid", "onResponse: "+photoResult.getFileID()+" ms ");


                    }else {
                        Log.d("tagged", "onResponse: "+response.body());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<PhotoResult> call, Throwable t) {
                Toast.makeText(getContext(), "fail", Toast.LENGTH_SHORT).show();
                try {
                    checkStatus.checkUser(true);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode==RESULT_OK&& data !=null){
            try {
                Log.d("tagged", "onActivityResult: ");
                imageUri = data.getData();
                imageStream = getContext().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                circleImageView.setImageBitmap(selectedImage);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

}
