package com.example.pubeo.tools.validation;

import android.content.Context;
import android.content.ContextWrapper;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TelephoneValidator extends ContextWrapper {

    private String telephoneNotValid;
    private final Pattern validTelephoneNumberPattern = Pattern.compile("\\d{10}");

    public TelephoneValidator(Context context){
        super(context);
        telephoneNotValid = context.getResources().getString(R.string.phoneNumberNotValid);
    }

    public final boolean isValid(String phoneStr) {
        Matcher matcher = this.validTelephoneNumberPattern.matcher((CharSequence)phoneStr);
        return matcher.find();
    }

    public final void phoneNumberValidation(TextInputLayout input) {
        String targetField = input.getEditText().getText().toString();
        if(!isValid(targetField))
            input.setError(telephoneNotValid);
        else
            input.setErrorEnabled(false);
    }
}
