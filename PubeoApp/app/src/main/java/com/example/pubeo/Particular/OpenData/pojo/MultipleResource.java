package com.example.pubeo.Particular.OpenData.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MultipleResource {


    @SerializedName("total")
    public Integer total;
    @SerializedName("data")
    public List<Datum> data = new ArrayList<>();

    public class Datum {

        @SerializedName("id")
        public Integer id;
        @SerializedName("name")
        public String name;
        @SerializedName("pantone_value")
        public String pantoneValue;

    }
}