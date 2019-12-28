package com.example.pubeo.Particular;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.pubeo.Particular.OpenData.TestODSActivity;
import com.example.pubeo.R;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pubeo.tools.constants.constantTags.PHONENUMBER_TAG;

public class ParticularCreateProfilActivity extends AppCompatActivity {

    @BindView(R.id.particularCreateProfilFirstName) TextInputLayout firstName;
    @BindView(R.id.particularCreateProfilLastName) TextInputLayout lastName;
    @BindView(R.id.particularCreateProfilGender) Spinner gender;
    @BindView(R.id.particularCreateProfilBirthDate) EditText birthDate;
    @BindView(R.id.particularCreateProfilNumber) TextInputLayout phoneNumber;
    @BindView(R.id.particularCreateProfilHomeAddress) TextInputLayout homeAddress;
    @BindView(R.id.particularCreateProfilCity) TextInputLayout city;
    @BindView(R.id.particularCreateProfilZipCode) TextInputLayout zipCode;
    @BindView(R.id.createProfilParticularButton) Button createProfil;
    @BindView(R.id.whiteArrowCreateProfil) ImageView arrowBack;
    final Calendar bdCalendar = Calendar.getInstance();

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

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                bdCalendar.set(Calendar.YEAR, year);
                bdCalendar.set(Calendar.YEAR, year);
                bdCalendar.set(Calendar.YEAR, year);
                updateLabel();
            }
        };

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(ParticularCreateProfilActivity.this,
                        date,
                        bdCalendar.get(Calendar.YEAR),
                        bdCalendar.get(Calendar.MONTH),
                        bdCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter);

        createProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkEmtpyField()){
                    Intent intent = new Intent(ParticularCreateProfilActivity.this, TestODSActivity.class);
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

        if(birthDate.getText().toString().isEmpty()) {
            isValid = false;
            birthDate.setError(getString(R.string.fieldNotEmpty));
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

    private void updateLabel(){
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        birthDate.setText(sdf.format(bdCalendar.getTime()));
    }

}
