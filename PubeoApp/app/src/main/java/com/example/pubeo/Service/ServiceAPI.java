package com.example.pubeo.Service;

import com.example.pubeo.model.Advertiser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAPI {
    @PUT("Professionnels/{id}")
    Call<Advertiser> putAdvertiser(@Path("id") String id, @Body Advertiser advertiser);

    @DELETE("Professionnels/{id}")
    Call<Advertiser> deleteAdvertiser(@Path("id") String id);
}
