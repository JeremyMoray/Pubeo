package com.example.pubeo.Particular.OpenData.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Manufacturers {

    @SerializedName("nhits")
    public Integer nhits;
    @SerializedName("facet_groups")
    public Groups facet_groups;

    public class Groups {

        @SerializedName("facets")
        public List<Facets> facets = new ArrayList<>();
        @SerializedName("name")
        public String name;

        public class Facets {

            @SerializedName("count")
            public Integer count;
            @SerializedName("path")
            public String path;
            @SerializedName("state")
            public String state;
            @SerializedName("name")
            public String name;
        }
    }
}
