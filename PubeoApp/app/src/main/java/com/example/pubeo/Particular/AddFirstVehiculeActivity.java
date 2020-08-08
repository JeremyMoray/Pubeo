package com.example.pubeo.Particular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddFirstVehiculeActivity extends AppCompatActivity {

    @BindView(R.id.particularAddFirstCarBrand) TextInputLayout carBrand;
    @BindView(R.id.particularAddFirstCarModel) TextInputLayout carModel;
    @BindView(R.id.AddVehiculeParticularButton) Button addButton;
    @BindView(R.id.whiteArrowCreateProfil) ImageView arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_first_vehicule);
        ButterKnife.bind(this);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFirstVehiculeActivity.super.onBackPressed();
            }
        });
    }
}
