package com.example.pubeo.tools.validation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.view.View;
import android.view.WindowManager;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class EmailValidator extends ContextWrapper {

    private String fieldNotValid;
    private final Pattern validEmailPattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public EmailValidator(Context context) {
        super(context);
        fieldNotValid = getResources().getString(R.string.fieldNotValid);
    }

    public final boolean isValid(String eMailStr) {
        Matcher matcher = this.validEmailPattern.matcher((CharSequence)eMailStr);
        return matcher.find();
    }

    public final void emailValidation(TextInputLayout input){
        String targetField = input.getEditText().getText().toString();
        if (!isValid(targetField)) {
            input.setError(fieldNotValid);
        } else {
            input.setErrorEnabled(false);
        }
    }
}
