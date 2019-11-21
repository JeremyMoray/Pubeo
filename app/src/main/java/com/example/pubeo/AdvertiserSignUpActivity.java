package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertiserSignUpActivity extends AppCompatActivity {

    @BindView(R.id.signUpButtonAdvertiser)  Button signUpButtonAdvertiser;
    private ImageView whiteArrowSignUpAdvertiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_sign_up);

        ButterKnife.bind(this);
        signUpButtonAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openCompanyInformationsActivity();
            }
        });

        whiteArrowSignUpAdvertiser = findViewById(R.id.whiteArrowSignUpAdvertiser);
        whiteArrowSignUpAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    public void openCompanyInformationsActivity(){
        Intent intent = new Intent(this, CompanyInformationsActivity.class);
        startActivity(intent);
    }
}
