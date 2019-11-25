package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyInformationsActivity extends AppCompatActivity {

    @BindView(R.id.continueInformationsAdvertiserButton) Button continueInformationsAdvertiserButton;
    private ImageView whiteArrowCompanyInformations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_informations);

        ButterKnife.bind(this);
        continueInformationsAdvertiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openHomeActivity();
            }
        });

        whiteArrowCompanyInformations = findViewById(R.id.whiteArrowCompanyInformations);
        whiteArrowCompanyInformations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    public void openHomeActivity(){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}
