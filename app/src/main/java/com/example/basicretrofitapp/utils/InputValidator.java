package com.example.basicretrofitapp.utils;

import android.content.Context;
import android.util.Patterns;

import com.example.basicretrofitapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class InputValidator {
    private Context context;
    private final List<Boolean> validations = new ArrayList();

    public InputValidator(Context context) {
        this.context = context;
    }

    public void isEmail(TextInputLayout til) {
        String input = til.getEditText().getText().toString();
        String errorMsg = this.context.getString(R.string.error_email);
        boolean isValid = Patterns.EMAIL_ADDRESS.matcher(input).matches();

        if (isValid) {
            til.setError(null);
            validations.add(true);
        } else {
            til.setError(errorMsg);
            til.getEditText().setText("");
            validations.add(false);
        }
    }

    public void isNumber(TextInputLayout til) {
        String input = til.getEditText().getText().toString();
        String errorMsg = this.context.getString(R.string.error_number);
        boolean isValid = Pattern.compile("^[0-9]*$").matcher(input).matches();
        if (isValid) {
            til.setError(null);
            validations.add(true);
        } else {
            til.setError(errorMsg);
            til.getEditText().setText("");
            validations.add(false);
        }
    }

    public void notZero(TextInputLayout til) {
        String input = til.getEditText().getText().toString();
        String errorMsg = this.context.getString(R.string.error_not_zero);
        try {
            if (Double.parseDouble(input) > 0) {
                til.setError(null);
                validations.add(true);
            } else {
                til.setError(errorMsg);
                til.getEditText().setText("");
                validations.add(false);
            }
        } catch (Exception e) {
            til.setError(errorMsg);
            til.getEditText().setText("");
            validations.add(false);
        }
    }

    public void isDate(TextInputLayout til) {
        String input = til.getEditText().getText().toString();
        String errorMsg = this.context.getString(R.string.error_date);
        boolean isValid = Pattern.compile("([12]\\d{3}-([1-9]|1[0-2])-([12]\\d|3[01]|0[1-9]))").matcher(input).matches();
        if (isValid) {
            til.setError(null);
            validations.add(true);
        } else {
            til.setError(errorMsg);
            til.getEditText().setText("");
            validations.add(false);
        }
    }

    public void isRequired(TextInputLayout til) {
        String input = til.getEditText().getText().toString();
        String errorMsg = this.context.getString(R.string.error_required);
        boolean isValid = input.trim().length() > 0;
        if (isValid) {
            til.setError(null);
            validations.add(true);
        } else {
            til.setError(errorMsg);
            til.getEditText().setText("");
            validations.add(false);
        }
    }

    public void isEqual(TextInputLayout rePassword, TextInputLayout password) {
        String input1 = rePassword.getEditText().getText().toString();
        String input2 = password.getEditText().getText().toString();

        String errorMsg = this.context.getString(R.string.error_password_equals);
        boolean isValid = input1.equals(input2) && input1.trim().length() > 0;
        if (isValid) {
            rePassword.setError(null);
            validations.add(true);
        } else {
            rePassword.setError(errorMsg);
            rePassword.getEditText().setText("");
            validations.add(false);
        }
    }

    public boolean validate() {
        for (boolean validate : this.validations) {
            if (!validate) {
                return false;
            }
        }
        return true;
    }
}

