package com.example.peaoplepassclientside.views;


import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.peaoplepassclientside.Constants;
import com.example.peaoplepassclientside.Font;
import com.example.peaoplepassclientside.R;
import com.example.peaoplepassclientside.network.ApiClient;
import com.example.peaoplepassclientside.network.ApiInterFace;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class InfoVisitorFragment extends Fragment {


    public InfoVisitorFragment() {
        // Required empty public constructor
    }

    TextView txtToolbar,
    txtName, txtStringPhone, txtPhone,txtStringMail,txtMail,txtStringPhoto,txtPhoto,txtSizePhoto,
    txtStringDoc, txtDoc, txtSizeDoc,txtHistoryVisit;
    ImageView iconBack;
    CircleImageView circleImageView;
    String name, phone, mail, photo, doc;
    ProgressBar progressBar;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments()!=null){
            name = getArguments().getString("name");
            phone = getArguments().getString("phone");
            photo = getArguments().getString("photo","null");
            mail = getArguments().getString("mail");
            doc = getArguments().getString("pdf","null");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_info_visitor, container, false);
        txtToolbar = v.findViewById(R.id.txt_info_visit_toolbar);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Загрузка...");
        progressBar = v.findViewById(R.id.progress_info);
        txtName =  v.findViewById(R.id.username_info_visitor);
        circleImageView = v.findViewById(R.id.img_info_visitor);
        iconBack = v.findViewById(R.id.back_info_visit);
        txtStringPhone = v.findViewById(R.id.phone_visitor_string);
        txtPhone = v.findViewById(R.id.phone_visitor_txt);
        txtStringMail =  v.findViewById(R.id.mail_visitor_string);
        txtMail = v.findViewById(R.id.mail_visiotr_txt);
        txtStringPhoto = v.findViewById(R.id.photo_doc_string);
        txtPhoto = v.findViewById(R.id.download_img_txt);
        txtSizePhoto = v.findViewById(R.id.size_of_photo_txt);
        txtSizePhoto.setPaintFlags(txtSizePhoto.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtStringDoc = v.findViewById(R.id.file_doc_string);
        txtDoc = v.findViewById(R.id.download_file_txt);
        txtSizeDoc = v.findViewById(R.id.size_of_file_txt);
        txtSizeDoc.setPaintFlags(txtSizeDoc.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        txtHistoryVisit = v.findViewById(R.id.history_info_visit);
        Font.montSerratMedium(getContext(),txtToolbar);
        Font.montSerrat(getContext(),txtName);
        Font.montSerrat(getContext(),txtStringPhone);
        Font.montSerrat(getContext(),txtPhone);
        Font.montSerrat(getContext(),txtStringMail);
        Font.montSerrat(getContext(),txtMail);
        Font.montSerrat(getContext(),txtStringPhoto);
        Font.montSerrat(getContext(),txtPhoto);
        Font.montSerrat(getContext(),txtStringDoc);
        Font.montSerrat(getContext(),txtDoc);
        Font.montSerrat(getContext(),txtSizeDoc);
        Font.montSerrat(getContext(),txtSizePhoto);
        Font.montSerrat(getContext(),txtHistoryVisit);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtName.setText(name);
        txtPhone.setText(phone);
        txtMail.setText(mail);
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        loadPhoto(photo);

    }
    private void loadPhoto(String photoId) {
        progressDialog.show();
        ApiInterFace apiInterface = ApiClient.apiRequest().create(ApiInterFace.class);
        Call<ResponseBody> call = apiInterface.getPhotoId(Constants.token,photoId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Log.d("tagged", "onResponse: base64"+response.body());
                try {
                    byte[] base64 = response.body().bytes();
                    Log.d("tagged", "onResponse: "+base64);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(base64, 0, base64.length);
                    circleImageView.setImageBitmap(decodedByte);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                // Log.d("tagged", "onFailure: "+t.getMessage());
                Toast.makeText(getContext(), "Не возможна загрузить фото", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
