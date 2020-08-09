package com.example.pubeo.Advertiser;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pubeo.R;
import com.example.pubeo.tools.CheckNetClass;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pubeo.tools.constants.constantTags.*;

public class AdvertiserSignUpActivity extends AppCompatActivity {

    @BindView(R.id.signUpButtonAdvertiser) Button signUpButtonAdvertiser;
    @BindView(R.id.mailAdvertiserSignUpField) TextInputLayout mailAdvertiserSignUpField;
    @BindView(R.id.mailAdvertiserSignUpEditText) TextInputEditText mailAdvertiserSignUpEditText;
    @BindView(R.id.passwordAdvertiserSignUpField) TextInputLayout passwordAdvertiserSignUpField;
    @BindView(R.id.passwordAdvertiserSignUpEditText) TextInputEditText passwordAdvertiserSignUpEditText;
    @BindView(R.id.passwordConfirmAdvertiserSignUpField) TextInputLayout passwordConfirmAdvertiserSignUpField;
    @BindView(R.id.passwordConfirmAdvertiserSignUpEditText) TextInputEditText passwordConfirmAdvertiserSignUpEditText;
    @BindView(R.id.whiteArrowSignUpAdvertiser) ImageView whiteArrowSignUpAdvertiser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser_sign_up);

        ButterKnife.bind(this);

        mailAdvertiserSignUpField.setTag(EMAIL_TAG);
        passwordAdvertiserSignUpField.setTag(PASSWORD_TAG);
        passwordConfirmAdvertiserSignUpField.setTag(PASSWORDCONFIRM_TAG);

        signUpButtonAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                openCompanyInformationsActivity();
            }
        });

        whiteArrowSignUpAdvertiser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        mailAdvertiserSignUpEditText
                .addTextChangedListener(new ValidationTextWatcher(this, mailAdvertiserSignUpField));

        passwordAdvertiserSignUpEditText
                .addTextChangedListener(new ValidationTextWatcher(this, passwordAdvertiserSignUpField));

        passwordConfirmAdvertiserSignUpEditText
                .addTextChangedListener(new ValidationTextWatcher(this, passwordConfirmAdvertiserSignUpField));
    }

    public void openCompanyInformationsActivity(){
        if(CheckNetClass.checknetwork(getApplicationContext())) {
            boolean isValid = true;

            if(mailAdvertiserSignUpField.isErrorEnabled() || passwordAdvertiserSignUpField.isErrorEnabled()){
                isValid = false;
            }
            else{
                if(mailAdvertiserSignUpField.getEditText().getText().toString().isEmpty()){
                    isValid = false;
                    mailAdvertiserSignUpField.setError(getString(R.string.fieldNotEmpty));
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
            }

            if (isValid) {
                Intent intent = new Intent(this, CompanyInformationsActivity.class);
                intent.putExtra("mail", mailAdvertiserSignUpField.getEditText().getText().toString());
                intent.putExtra("password", passwordAdvertiserSignUpField.getEditText().getText().toString());
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, R.string.lossConnection, Toast.LENGTH_SHORT).show();
        }
    }
}
