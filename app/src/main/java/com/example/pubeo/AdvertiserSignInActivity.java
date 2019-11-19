package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdvertiserSignInActivity extends AppCompatActivity {

    private ImageView whiteArrowSignInAdvertiser;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_sign_in);
        whiteArrowSignInAdvertiser = findViewById(R.id.whiteArrowSignInAdvertiser);
        whiteArrowSignInAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
