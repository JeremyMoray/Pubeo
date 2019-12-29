package com.example.pubeo.Service;

import com.example.pubeo.model.Advertiser;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceAPI {
    @PUT("Professionnels/{nomEntreprise}")
    Call<Advertiser> patchPost(@Path("nomEntreprise") String nomEntreprise, @Body Advertiser advertiser);
}
