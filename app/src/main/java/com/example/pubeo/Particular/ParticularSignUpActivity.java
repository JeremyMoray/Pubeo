package com.example.pubeo.Particular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pubeo.R;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pubeo.tools.constants.constantTags.EMAIL_TAG;
import static com.example.pubeo.tools.constants.constantTags.PASSWORDCONFIRM_TAG;
import static com.example.pubeo.tools.constants.constantTags.PASSWORD_TAG;
import static com.example.pubeo.tools.constants.constantTags.USERNAME_TAG;

public class ParticularSignUpActivity extends AppCompatActivity {

    @BindView(R.id.particularSignUpEmail) TextInputLayout particularSignUpEmail;
    @BindView(R.id.particularSignUpEmailEditText) TextInputEditText particularSignUpEmailEditText;
    @BindView(R.id.particularSignUpUsername) TextInputLayout particularSignUpUsername;
    @BindView(R.id.particularSignUpUsernameEditText) TextInputEditText particularSignUpUsernameEditText;
    @BindView(R.id.particularSignUpPwd) TextInputLayout particularSignUpPwd;
    @BindView(R.id.particularSignUpPwdEditText) TextInputEditText particularSignUpPwdEditText;
    @BindView(R.id.particularSignUpConfirmPwd) TextInputLayout particularSignUpConfirmPwd;
    @BindView(R.id.particularSignUpArrowback) ImageButton particularSignUpArrowback;
    @BindView(R.id.signUpButton) Button signUpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_sign_up);
        ButterKnife.bind(this);
        particularSignUpEmail.setTag(EMAIL_TAG);
        particularSignUpUsername.setTag(USERNAME_TAG);
        particularSignUpPwd.setTag(PASSWORD_TAG);
        particularSignUpConfirmPwd.setTag(PASSWORDCONFIRM_TAG);
        particularSignUpConfirmPwd.setEnabled(false);

        particularSignUpArrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParticularSignUpActivity.super.onBackPressed();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConfirmPassword();
            }
        });

        particularSignUpEmailEditText
                .addTextChangedListener(new ValidationTextWatcher(this, particularSignUpEmail));

        particularSignUpUsernameEditText
                .addTextChangedListener(new ValidationTextWatcher(this, particularSignUpUsername));

        particularSignUpPwdEditText
                .addTextChangedListener(new ValidationTextWatcher(this, particularSignUpPwd));

        particularSignUpPwdEditText
                .addTextChangedListener(new ValidationTextWatcher(this, particularSignUpConfirmPwd));

    }

    public void checkConfirmPassword(){
        if(!particularSignUpPwd.getEditText().getText().toString().equals(particularSignUpConfirmPwd.getEditText().getText().toString())) {
            particularSignUpPwd.setError(getString(R.string.passwordNotMatching));
            particularSignUpConfirmPwd.setError(getString(R.string.passwordNotMatching));
        } else {
            particularSignUpPwd.setErrorEnabled(false);
            particularSignUpConfirmPwd.setErrorEnabled(false);
        }
    }
}
