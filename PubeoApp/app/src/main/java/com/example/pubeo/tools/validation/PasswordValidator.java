package com.example.pubeo.tools.validation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator extends ContextWrapper {

    private String passwordTooShort;
    private String passwordTooLong;
    private String passwordWithOneNumber;
    private final Pattern oneNumberMinPattern = Pattern.compile(".*[0-9].*");

    public PasswordValidator(Context context) {
        super(context);
        passwordTooShort = getResources().getString(R.string.passwordTooShort);
        passwordTooLong = getResources().getString(R.string.passwordTooLong);
        passwordWithOneNumber = getResources().getString(R.string.passwordWithOneNumber);
    }

    public final boolean isValid(String password) {
        Matcher matcher = this.oneNumberMinPattern.matcher((CharSequence)password);
        return matcher.find();
    }

    public final void passwordValidation(TextInputLayout input) {
        String targetField = input.getEditText().getText().toString();
        if(targetField.length() < 8) {
            input.setError(passwordTooShort);
        } else if (targetField.length() > 30) {
            input.setError(passwordTooLong);
        } else if (!isValid(targetField)) {
            input.setError(passwordWithOneNumber);
        } else {
            input.setErrorEnabled(false);
        }
    }
}
