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

public class AdvertiserSignUpActivity extends AppCompatActivity {

    @BindView(R.id.signUpButtonAdvertiser) Button signUpButtonAdvertiser;
    @BindView(R.id.mailAdvertiserSignUpField) TextInputLayout mailAdvertiserSignUpField;
    @BindView(R.id.passwordAdvertiserSignUpField) TextInputLayout passwordAdvertiserSignUpField;
    @BindView(R.id.passwordConfirmAdvertiserSignUpField) TextInputLayout passwordConfirmAdvertiserSignUpField;
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
        boolean isValid = true;

        if(mailAdvertiserSignUpField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            mailAdvertiserSignUpField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            mailAdvertiserSignUpField.setErrorEnabled(false);
        }

        if(passwordAdvertiserSignUpField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            passwordAdvertiserSignUpField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            if(!passwordAdvertiserSignUpField.getEditText().getText().toString().equals(passwordConfirmAdvertiserSignUpField.getEditText().getText().toString())){
                isValid = false;
                passwordAdvertiserSignUpField.setError(getString(R.string.passwordNotMatching));
                passwordConfirmAdvertiserSignUpField.setError(getString(R.string.passwordNotMatching));
            }
            else{
                passwordAdvertiserSignUpField.setErrorEnabled(false);
                passwordConfirmAdvertiserSignUpField.setErrorEnabled(false);
            }
        }

        if (isValid) {
            Intent intent = new Intent(this, CompanyInformationsActivity.class);
            startActivity(intent);
        }
    }
}
