package com.example.pubeo.tools.validation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.pubeo.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class ValidationTextWatcher extends ContextWrapper implements TextWatcher  {
    /*
    tag 1 : Email
    tag 2 : Password
     */

    private View view;
    private EmailValidator emailValidator = new EmailValidator(this);
    private PasswordValidator passwordValidator = new PasswordValidator(this);
    private UsernameValidator usernameValidator = new UsernameValidator(this);

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
        }
    }
}
