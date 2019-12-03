package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertiserSignInActivity extends AppCompatActivity {

    @BindView(R.id.signInButtonAdvertiser)Button signInButtonAdvertiser;
    private ImageView whiteArrowSignInAdvertiser;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_sign_in);

        ButterKnife.bind(this);
        signInButtonAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openHomeActivity();
            }
        });

        whiteArrowSignInAdvertiser = findViewById(R.id.whiteArrowSignInAdvertiser);
        whiteArrowSignInAdvertiser.setOnClickListener(new View.OnClickListener() {
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
