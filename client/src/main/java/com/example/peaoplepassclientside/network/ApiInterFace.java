package com.example.peaoplepassclientside.network;

import androidx.media.VolumeProviderCompat;

import com.example.peaoplepassclientside.model.AddVisitBody;
import com.example.peaoplepassclientside.model.AddVisitResponse;
import com.example.peaoplepassclientside.model.BodyVisitors;
import com.example.peaoplepassclientside.model.DeleteResponse;
import com.example.peaoplepassclientside.model.Employe;
import com.example.peaoplepassclientside.model.PhoneCall;
import com.example.peaoplepassclientside.model.PhoneVerification;
import com.example.peaoplepassclientside.model.PhotoResult;
import com.example.peaoplepassclientside.model.ResponseEmploye;
import com.example.peaoplepassclientside.model.User;
import com.example.peaoplepassclientside.model.UserRegistration;
import com.example.peaoplepassclientside.model.UserUpdate;
import com.example.peaoplepassclientside.visitorModel.VisitorsResult;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterFace {

    @Headers({
            "Content-Type:application/json"
    })
    @POST("authorize/sms")
    Call<PhoneVerification> sendSms(@Body PhoneCall phoneCall);


    @Multipart
    @POST("visitor/photo")
    Call<PhotoResult> uploadPhoto(@Header("JWT")String token,@Part MultipartBody.Part file);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("authorize/register")
    Call<ResponseBody> registerUser(@Body UserRegistration userRegistration);


    @Headers({
            "Content-Type:application/json"
    })
    @POST("employ")
    Call<ResponseEmploye> getEmploye(@Header("JWT")String jwt,@Body Employe employe);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("visitor/update")
    Call<UserUpdate> updateUser(@Header("JWT")String jwt,@Body UserUpdate userRegistration);


    @Headers({
            "Content-Type:application/json"
    })
    @POST("request")
    Call<VisitorsResult> getVisitors(@Header(
            "JWT"
    )String token,
                                     @Body BodyVisitors bodyVisitors);


    @Headers({
            "Content-Type:application/json"
    })
    @POST("request/add")
    Call<AddVisitResponse> addVisit(@Header("JWT")String token, @Body AddVisitBody addVisitBody);


    @Headers({
            "Content-Type:application/json"
    })
    @POST("request/update")
    Call<AddVisitResponse> updatePass(@Header("JWT")String token, @Body AddVisitBody addVisitBody);



    @Headers({
            "Content-Type:application/json"
    })
    @GET("visitor/photo/{photoID}/")
    Call<ResponseBody> getPhotoId(@Header("JWT")String token, @Path("photoID")String str);

    @Headers({
            "Content-Type:application/json"
    })
    @GET("request/{id}/delete")
    Call<DeleteResponse> deleteOrder(@Header("JWT")String token, @Path("id")String id);

    @Headers({
            "Content-Type:application/json"
    })
    @POST("authorize/login")
    Call<ResponseBody> loginUser(@Body User user);


}
