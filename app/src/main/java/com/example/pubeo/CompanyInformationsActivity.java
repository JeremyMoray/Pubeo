package com.example.pubeo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CompanyInformationsActivity extends AppCompatActivity {

    @BindView(R.id.continueInformationsAdvertiserButton) Button continueInformationsAdvertiserButton;
    @BindView(R.id.companyNameField) TextInputLayout companyNameField;
    @BindView(R.id.companyVATField) TextInputLayout companyVATField;
    @BindView(R.id.companyPhoneField) TextInputLayout companyPhoneField;
    @BindView(R.id.companyAddressField) TextInputLayout companyAddressField;
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
        boolean isValid = true;

        if(companyNameField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            companyNameField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            companyNameField.setErrorEnabled(false);
        }

        if(companyVATField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            companyVATField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            companyVATField.setErrorEnabled(false);
        }

        if(companyPhoneField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            companyPhoneField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            companyPhoneField.setErrorEnabled(false);
        }

        if(companyAddressField.getEditText().getText().toString().isEmpty()){
            isValid = false;
            companyAddressField.setError(getString(R.string.fieldNotEmpty));
        }
        else{
            companyAddressField.setErrorEnabled(false);
        }

        if (isValid) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }
}
