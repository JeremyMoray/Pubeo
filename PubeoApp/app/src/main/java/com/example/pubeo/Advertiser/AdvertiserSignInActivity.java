package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdvertiserSignInActivity extends AppCompatActivity {

    @BindView(R.id.signInButtonAdvertiser)Button signInButtonAdvertiser;
    @BindView(R.id.mailAdvertiserSignInField)TextInputLayout mailAdvertiserSignInField;
    @BindView(R.id.passwordAdvertiserSignInField)TextInputLayout passwordAdvertiserSignInField;
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
        boolean isValid = true;

        if(mailAdvertiserSignInField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            mailAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            mailAdvertiserSignInField.setErrorEnabled(false);
        }

        if(passwordAdvertiserSignInField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            passwordAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            passwordAdvertiserSignInField.setErrorEnabled(false);
        }

        if (isValid) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
