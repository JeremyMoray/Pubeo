package com.example.pubeo.Particular;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.DAO.ParticularDAO;
import com.example.pubeo.DTO.ParticularCreateDTO;
import com.example.pubeo.R;
import com.example.pubeo.model.Login;
import com.example.pubeo.model.Particular;
import com.example.pubeo.model.Token;
import com.example.pubeo.tools.CheckNetClass;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pubeo.tools.constants.constantTags.PHONENUMBER_TAG;

public class ParticularCreateProfilActivity extends AppCompatActivity {

    @BindView(R.id.createProfilParticularButton) Button createProfil;
    @BindView(R.id.particularCreateProfilFirstName) TextInputLayout firstName;
    @BindView(R.id.particularCreateProfilLastName) TextInputLayout lastName;
    @BindView(R.id.particularCreateProfilBirthDate) EditText birthDate;
    @BindView(R.id.particularCreateProfilNumber) TextInputLayout phoneNumber;
    @BindView(R.id.particularCreateProfilHomeAddress) TextInputLayout homeAddress;
    @BindView(R.id.particularCreateProfilCity) TextInputLayout city;
    @BindView(R.id.particularCreateProfilZipCode) TextInputLayout zipCode;
    @BindView(R.id.whiteArrowCreateProfil) ImageView arrowBack;

    final Calendar bdCalendar = Calendar.getInstance();
    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_create_profil);

        ButterKnife.bind(this);

        phoneNumber.setTag(PHONENUMBER_TAG);

        createProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                bdCalendar.set(Calendar.YEAR, year);
                bdCalendar.set(Calendar.MONTH, month);
                bdCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
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

        phoneNumber.getEditText()
                .addTextChangedListener(new ValidationTextWatcher(this, phoneNumber));
    }

    public void openHomeActivity(){
        if(CheckNetClass.checknetwork(getApplicationContext())) {
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

            if(isValid){
                ParticularDAO particularDAO = new ParticularDAO();
                String postalCode = (zipCode.getEditText() == null?null:zipCode.getEditText().getText().toString());

                ParticularCreateDTO particular = new ParticularCreateDTO(
                        firstName.getEditText().getText().toString(),
                        lastName.getEditText().getText().toString(),
                        homeAddress.getEditText().getText().toString(),
                        getIntent().getStringExtra("username"),
                        dateStringToDateFormat(birthDate.getText().toString()),
                        phoneNumber.getEditText().getText().toString(),
                        getIntent().getStringExtra("mail"),
                        getIntent().getStringExtra("password"),
                        postalCode
                );

                Call<Particular> call = particularDAO.addParticular(particular);

                call.enqueue(new Callback<Particular>() {
                    @Override
                    public void onResponse(Call<Particular> call, Response<Particular> response) {
                        if(response.isSuccessful()){
                            Login login = new Login(particular.getMail(), particular.getMotDePasse());

                            Call<Token> tokenCall = particularDAO.login(login);
                            tokenCall.enqueue(new Callback<Token>() {
                                @Override
                                public void onResponse(Call<Token> call, Response<Token> response) {
                                    if(response.isSuccessful()){
                                        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();

                                        editor.putString("access_token", "Bearer " + response.body().getAccess_token());
                                        editor.apply();
                                        Intent intent = new Intent(getApplicationContext(), ParticularHomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                }

                                @Override
                                public void onFailure(Call<Token> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<Particular> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
        else {
            Toast.makeText(this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
        }
    }

    private Date dateStringToDateFormat(String date){
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        try {
            return sdf.parse(date);
        }
        catch (ParseException ex) {
            return null;
        }
    }

    private void updateLabel(){
        String format = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());

        birthDate.setText(sdf.format(bdCalendar.getTime()));
    }

}
