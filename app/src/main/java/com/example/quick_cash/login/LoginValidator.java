package com.example.quick_cash.login;

import java.util.regex.Pattern;
public class LoginValidator {

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    // temporary strings for authentication for now
    private static final String DEMO_EMAIL = "user@example.com";
    private static final String DEMO_PASS  = "abc123";

    public boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return EMAIL_REGEX.matcher(email.trim()).matches();
    }

    public boolean isPasswordValid(String password) {
        if (password == null) return false;
        return password.length() >= 6;
    }

    // to be moved later to a repo
    public boolean authenticate(String email, String password) {
        if (!isEmailValid(email) || !isPasswordValid(password)) return false;

        // mock success path for now
        return DEMO_EMAIL.equalsIgnoreCase(email.trim()) && DEMO_PASS.equals(password);
    }
}