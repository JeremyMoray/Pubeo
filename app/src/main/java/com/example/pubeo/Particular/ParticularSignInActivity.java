package com.example.pubeo.Particular;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.pubeo.R;
import com.example.pubeo.tools.validation.EmailValidator;
import com.example.pubeo.tools.validation.ValidationTextWatcher;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.pubeo.tools.constants.constantTags.EMAIL_TAG;
import static com.example.pubeo.tools.constants.constantTags.PASSWORD_TAG;

public class ParticularSignInActivity extends AppCompatActivity {

    @BindView(R.id.particularSignInEmail) TextInputLayout particularSignInEmail;
    @BindView(R.id.particularSignInEmailEditText) TextInputEditText particularSignInEmailEditText;
    @BindView(R.id.particularSignInPwd) TextInputLayout particularSignInPwd;
    @BindView(R.id.particularSignInPwdEditText) TextInputEditText particularSignInPwdEditText;
    @BindView(R.id.particularSignInButton) Button particularSignInButton;
    @BindView(R.id.particularSignInArrowback)ImageButton particularSignInArrowback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_sign_in);
        ButterKnife.bind(this);
        particularSignInEmail.setTag(EMAIL_TAG);
        particularSignInPwd.setTag(PASSWORD_TAG);

        particularSignInArrowback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParticularSignInActivity.super.onBackPressed();
            }
        });

        particularSignInEmailEditText.addTextChangedListener(new ValidationTextWatcher(this, particularSignInEmail));
        particularSignInPwdEditText.addTextChangedListener(new ValidationTextWatcher(this, particularSignInPwd));

        particularSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
