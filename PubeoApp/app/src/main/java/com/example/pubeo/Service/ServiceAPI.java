package com.example.pubeo.Service;

import com.example.pubeo.DTO.AdvertiserUpdateDTO;
import com.example.pubeo.model.Advertiser;
import com.example.pubeo.model.LoginAdvertiser;
import com.example.pubeo.model.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAPI {

    @POST("Jwt/LoginAdvertiser")
    Call<Token> loginAdvertiser(@Body LoginAdvertiser loginAdvertiser);

    @GET("Professionnels/GetMe")
    Call<Advertiser> getMeAdvertiser(@Header("Authorization") String token);

    @PUT("Professionnels/UpdateMyAccount")
    Call<Void> updateAdvertiser(@Header("Authorization") String token, @Body AdvertiserUpdateDTO advertiser);

    @DELETE("Professionnels/{id}")
    Call<Advertiser> deleteAdvertiser(@Path("id") String id);

    @POST("Professionnels")
    Call<Advertiser> addAdvertiser(@Body Advertiser advertiser);
}
