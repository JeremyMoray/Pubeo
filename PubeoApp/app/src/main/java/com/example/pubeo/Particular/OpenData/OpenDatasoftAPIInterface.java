package com.example.pubeo.Particular.OpenData;

import com.example.pubeo.Particular.OpenData.pojo.Manufacturers;

import retrofit2.Call;
import retrofit2.http.GET;

public interface OpenDatasoftAPIInterface {

    @GET("/api/records/1.0/search/?dataset=vehicules-commercialises%40public&rows=0&facet=marque")
    Call<Manufacturers> getManufacturerdList();

}
