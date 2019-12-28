package com.example.pubeo.Particular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pubeo.R;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pubeo.tools.constants.constantTags.PHONENUMBER_TAG;

public class ParticularCreateProfilActivity extends AppCompatActivity {

    @BindView(R.id.particularCreateProfilFirstName) TextInputLayout firstName;
    @BindView(R.id.particularCreateProfilLastName) TextInputLayout lastName;
    @BindView(R.id.particularCreateProfilGender) Spinner gender;
    @BindView(R.id.particularCreateProfilNumber) TextInputLayout phoneNumber;
    @BindView(R.id.particularCreateProfilHomeAddress) TextInputLayout homeAddress;
    @BindView(R.id.particularCreateProfilCity) TextInputLayout city;
    @BindView(R.id.particularCreateProfilZipCode) TextInputLayout zipCode;
    @BindView(R.id.createProfilParticularButton) Button createProfil;
    @BindView(R.id.whiteArrowCreateProfil) ImageView arrowBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_create_profil);
        ButterKnife.bind(this);
        phoneNumber.setTag(PHONENUMBER_TAG);

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParticularCreateProfilActivity.super.onBackPressed();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        createProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmtpyField()){
                    Intent intent = new Intent(ParticularCreateProfilActivity.this, ParticularHomeActivity.class);
                    startActivity(intent);
                }
            }
        });

        phoneNumber.getEditText()
                .addTextChangedListener(new ValidationTextWatcher(this, phoneNumber));
    }

    public boolean checkEmtpyField() {
        boolean isValid = true;

        if(firstName.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            firstName.setError(getString(R.string.fieldNotEmpty));
        }
        else {
            firstName.setErrorEnabled(false);
        }

        if(lastName.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            lastName.setError(getString(R.string.fieldNotEmpty));
        }
        else {
            lastName.setErrorEnabled(false);
        }

        if(phoneNumber.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            phoneNumber.setError(getString(R.string.fieldNotEmpty));
        }
        else {
            phoneNumber.setErrorEnabled(false);
        }

        if(homeAddress.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            homeAddress.setError(getString(R.string.fieldNotEmpty));
        }
        else {
            homeAddress.setErrorEnabled(false);
        }

        if(city.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            city.setError(getString(R.string.fieldNotEmpty));
        }
        else {
            city.setErrorEnabled(false);
        }

        if(zipCode.getEditText().getText().toString().isEmpty()) {
            isValid = false;
            zipCode.setError(getString(R.string.fieldNotEmpty));
        }
        else {
            zipCode.setErrorEnabled(false);
        }

        return isValid;
    }

}
