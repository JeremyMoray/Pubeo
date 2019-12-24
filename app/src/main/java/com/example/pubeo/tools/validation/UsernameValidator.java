package com.example.pubeo.tools.validation;

import android.content.Context;
import android.content.ContextWrapper;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsernameValidator extends ContextWrapper {

    private String usernameNotValid;
    private final Pattern validUsernamePattern = Pattern.compile("^[a-z0-9_-]{3,15}$");

    public UsernameValidator(Context context) {
        super(context);
        usernameNotValid = getResources().getString(R.string.usernameNotValid);
    }

    public final boolean isValid(String usernameStr) {
        Matcher matcher = this.validUsernamePattern.matcher((CharSequence)usernameStr);
        return matcher.find();
    }

    public final void usernameValidation(TextInputLayout input){
        String targetField = input.getEditText().getText().toString();
        if(!isValid(targetField)) input.setError(usernameNotValid);
        else input.setErrorEnabled(false);
    }
}
