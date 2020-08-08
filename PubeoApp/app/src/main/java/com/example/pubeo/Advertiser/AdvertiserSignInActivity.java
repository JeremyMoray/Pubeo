package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pubeo.DAO.AdvertiserDAO;
import com.example.pubeo.R;
import com.example.pubeo.model.Login;
import com.example.pubeo.model.Token;
import com.example.pubeo.tools.CheckNetClass;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdvertiserSignInActivity extends AppCompatActivity {

    @BindView(R.id.signInButtonAdvertiser)Button signInButtonAdvertiser;
    @BindView(R.id.mailAdvertiserSignInField)TextInputLayout mailAdvertiserSignInField;
    @BindView(R.id.passwordAdvertiserSignInField)TextInputLayout passwordAdvertiserSignInField;
    @BindView(R.id.whiteArrowSignInAdvertiser) ImageView whiteArrowSignInAdvertiser;

    public static final String SHARED_PREFS = "sharedPrefs";

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

        whiteArrowSignInAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });
    }

    public void openHomeActivity(){
        if(CheckNetClass.checknetwork(getApplicationContext())) {
            boolean isValid = true;

            if (mailAdvertiserSignInField.getEditText().getText().toString().isEmpty()) {
                isValid = false;
                mailAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
            }
            else {
                mailAdvertiserSignInField.setErrorEnabled(false);
            }

            if (passwordAdvertiserSignInField.getEditText().getText().toString().isEmpty()) {
                isValid = false;
                passwordAdvertiserSignInField.setError(getString(R.string.fieldNotEmpty));
            }
            else {
                passwordAdvertiserSignInField.setErrorEnabled(false);
            }

            if (isValid) {
                Login login = new Login(mailAdvertiserSignInField.getEditText().getText().toString(), passwordAdvertiserSignInField.getEditText().getText().toString());
                AdvertiserDAO advertiserDAO = new AdvertiserDAO();

                Call<Token> call = advertiserDAO.login(login);

                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful()) {
                            SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("access_token", "Bearer " + response.body().getAccess_token());
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }

                        if (response.code() == 404)
                            mailAdvertiserSignInField.setError(getString(R.string.mailNotExist));

                        if (response.code() == 401)
                            passwordAdvertiserSignInField.setError(getString(R.string.passwordIncorrect));
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(AdvertiserSignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else {
            Toast.makeText(this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
        }
    }
}
