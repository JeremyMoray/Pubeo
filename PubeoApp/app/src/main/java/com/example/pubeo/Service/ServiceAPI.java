package com.example.pubeo.Service;

import com.example.pubeo.model.Advertiser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAPI {
    @PUT("Professionnels/{id}")
    Call<Advertiser> patchPost(@Path("id") String id, @Body Advertiser advertiser);
}
