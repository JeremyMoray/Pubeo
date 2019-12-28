package com.example.pubeo.Particular.OpenData;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pubeo.Particular.OpenData.pojo.Manufacturers;
import com.example.pubeo.Particular.OpenData.pojo.MultipleResource;
import com.example.pubeo.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TestODSActivity extends AppCompatActivity {

    @BindView(R.id.responseText)
    TextView responseText;
    OpenDatasoftAPIInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_ods);
        ButterKnife.bind(this);
        api = OpenDatasoftAPIClient.getClient().create(OpenDatasoftAPIInterface.class);


        /*
        Call<MultipleResource> call = api.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {


                Log.d("TAG",response.code()+"");

                String displayResponse = "";

                MultipleResource resource = response.body();
                Integer total = resource.total;
                List<MultipleResource.Datum> datumList = resource.data;

                displayResponse += total + " Total\n";

                for (MultipleResource.Datum datum : datumList) {
                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " +  "\n";
                }

                responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });
         */


        Call<Manufacturers> call = api.getManufacturerdList();
        call.enqueue(new Callback<Manufacturers>() {
            @Override
            public void onResponse(Call<Manufacturers> call, Response<Manufacturers> response) {
                String displayResponse = "";

                Manufacturers manufacturers = response.body();
                Integer nhits = manufacturers.nhits;
                Manufacturers.Groups groups = manufacturers.facet_groups;
                List<Manufacturers.Groups.Facets> facetsList = groups.facets;

                displayResponse += nhits + " nhits\n";

                for (Manufacturers.Groups.Facets facet : facetsList) {
                    displayResponse += facet.count + " " + facet.path + " " + facet.state + " " + facet.name + "\n";
                }

                responseText.setText(displayResponse);
            }

            @Override
            public void onFailure(Call<Manufacturers> call, Throwable t) {
                call.cancel();
            }
        });
    }
}
