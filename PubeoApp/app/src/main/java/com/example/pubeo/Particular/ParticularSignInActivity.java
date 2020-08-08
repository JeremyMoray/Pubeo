package com.example.pubeo.Particular;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.pubeo.Advertiser.AdvertiserSignInActivity;
import com.example.pubeo.Advertiser.CompanyInformationsActivity;
import com.example.pubeo.Advertiser.HomeActivity;
import com.example.pubeo.DAO.ParticularDAO;
import com.example.pubeo.R;
import com.example.pubeo.model.Login;
import com.example.pubeo.model.Token;
import com.example.pubeo.tools.CheckNetClass;
import com.example.pubeo.tools.validation.EmailValidator;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.pubeo.tools.constants.constantTags.EMAIL_TAG;
import static com.example.pubeo.tools.constants.constantTags.PASSWORD_TAG;

public class ParticularSignInActivity extends AppCompatActivity {

    @BindView(R.id.particularSignInButton) Button particularSignInButton;
    @BindView(R.id.particularSignInEmail) TextInputLayout particularSignInEmail;
    @BindView(R.id.particularSignInEmailEditText) TextInputEditText particularSignInEmailEditText;
    @BindView(R.id.particularSignInPwd) TextInputLayout particularSignInPwd;
    @BindView(R.id.particularSignInPwdEditText) TextInputEditText particularSignInPwdEditText;
    @BindView(R.id.particularSignInArrowback) ImageButton particularSignInArrowback;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_sign_in);
        ButterKnife.bind(this);
        particularSignInEmail.setTag(EMAIL_TAG);
        particularSignInPwd.setTag(PASSWORD_TAG);

        particularSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomeActivity();
            }
        });

        particularSignInArrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        particularSignInEmailEditText
                .addTextChangedListener(new ValidationTextWatcher(this, particularSignInEmail));
        particularSignInPwdEditText
                .addTextChangedListener(new ValidationTextWatcher(this, particularSignInPwd));
    }

    public void openHomeActivity(){
        if(CheckNetClass.checknetwork(getApplicationContext())) {
            boolean isValid = true;

            if(particularSignInEmailEditText.getText().toString().isEmpty()) {
                isValid = false;
                particularSignInEmail.setError(getString(R.string.fieldNotEmpty));
            }
            else {
                particularSignInEmail.setErrorEnabled(false);
            }

            if(particularSignInPwdEditText.getText().toString().isEmpty()) {
                isValid = false;
                particularSignInPwd.setError(getString(R.string.fieldNotEmpty));
            }
            else {
                particularSignInPwd.setErrorEnabled(false);
            }

            if(isValid){
                Login login = new Login(particularSignInEmail.getEditText().getText().toString(), particularSignInPwd.getEditText().getText().toString());
                ParticularDAO particulierDAO = new ParticularDAO();

                Call<Token> call = particulierDAO.login(login);

                call.enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        if (response.isSuccessful()) {
                            SharedPreferences sharedPref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString("access_token", "Bearer " + response.body().getAccess_token());
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), ParticularHomeActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(ParticularSignInActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        else {
            Toast.makeText(this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
        }
    }
}
