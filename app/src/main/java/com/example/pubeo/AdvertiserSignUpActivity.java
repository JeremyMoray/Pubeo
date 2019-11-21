package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class AdvertiserSignUpActivity extends AppCompatActivity {

    private ImageView whiteArrowSignUpAdvertiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_sign_up);

        whiteArrowSignUpAdvertiser = findViewById(R.id.whiteArrowSignUpAdvertiser);
        whiteArrowSignUpAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
