package com.example.quick_cash.controllers;

import java.util.regex.Pattern;

public class LoginValidator {

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    /**
     * Is email valid boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return EMAIL_REGEX.matcher(email.trim()).matches();
    }

    /**
     * Is password valid boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public boolean isPasswordValid(String password) {
        if (password == null) return false;
        return password.length() >= 6;
    }
}