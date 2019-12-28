package com.example.pubeo.tools.validation;

import android.content.Context;
import android.content.ContextWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.google.android.material.textfield.TextInputLayout;

public class ValidationTextWatcher extends ContextWrapper implements TextWatcher  {

    private View view;
    private EmailValidator emailValidator = new EmailValidator(this);
    private PasswordValidator passwordValidator = new PasswordValidator(this);
    private UsernameValidator usernameValidator = new UsernameValidator(this);
    private TelephoneValidator telephoneValidator = new TelephoneValidator(this);

    public ValidationTextWatcher(Context context, View view) {
        super(context);
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) { }

    @Override
    public void afterTextChanged(Editable s) {
        Object tag = view.getTag();
        if(tag.equals(1)) {
            emailValidator.emailValidation((TextInputLayout) view);
        } else if (tag.equals(2)) {
            passwordValidator.passwordValidation((TextInputLayout) view);
        } else if (tag.equals(3)) {
            usernameValidator.usernameValidation((TextInputLayout) view);
        } else if (tag.equals(4)) {
            view.setEnabled(true);
        } else if (tag.equals(5)) {
            telephoneValidator.phoneNumberValidation((TextInputLayout) view);
        }
    }
}
