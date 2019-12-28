package com.example.pubeo.Services;

import com.example.pubeo.model.Advertiser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceAPI {

    String BASE_URL = "https://10.0.2.2:5001/";

    @GET("Professionnels")
    Call<List<Advertiser>> getAdvertisers();
}
