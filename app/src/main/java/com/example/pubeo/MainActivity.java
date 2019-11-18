package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.signInButton) Button SignInButton;
    @BindView(R.id.signUpButton) Button SignUpButton;
    @BindView(R.id.advertiserButton) RadioButton advertiserButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(advertiserButton.isChecked()){
                    openAdvertiserSignInActivity();
                }
                else{
                    openParticularSignInActivity();
                }
            }
        });

        SignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(advertiserButton.isChecked()){
                    openAdvertiserSignUpActivity();
                }
                else{
                    openParticularSignUpActivity();
                }
            }
        });
    }

    public void openAdvertiserSignInActivity(){
        Intent intent = new Intent(this, AdvertiserSignInActivity.class);
        startActivity(intent);
    }

    public void openParticularSignInActivity(){
        Intent intent = new Intent(this, ParticularSignInActivity.class);
        startActivity(intent);
    }

    public void openAdvertiserSignUpActivity(){
        Intent intent = new Intent(this, AdvertiserSignUpActivity.class);
        startActivity(intent);
    }

    public void openParticularSignUpActivity(){
        Intent intent = new Intent(this, ParticularSignUpActivity.class);
        startActivity(intent);
    }
}
