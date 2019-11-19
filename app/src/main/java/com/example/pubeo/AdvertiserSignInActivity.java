package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;

public class AdvertiserSignInActivity extends AppCompatActivity {

    @BindView(R.id.whiteArrowSignInAdvertiser) ImageView whiteArrowSignInAdvertiser;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_sign_in);

        whiteArrowSignInAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }
}
