package com.example.quick_cash.login;

import java.util.regex.Pattern;
public class LoginValidator {
    private final LoginRepository repository;

    private static final Pattern EMAIL_REGEX = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$",
            Pattern.CASE_INSENSITIVE
    );

    // temporary strings for authentication for now
    private static final String DEMO_EMAIL = "user@example.com";
    private static final String DEMO_PASS  = "abc123";

    public LoginValidator() {
        // mock for now until Firebase is implemented
        this.repository = new TempLoginRepository();
    }

    public boolean isEmailValid(String email) {
        if (email == null || email.trim().isEmpty()) return false;
        return EMAIL_REGEX.matcher(email.trim()).matches();
    }

    public boolean isPasswordValid(String password) {
        if (password == null) return false;
        return password.length() >= 6;
    }

    public boolean authenticate(String email, String password) {
        if (!isEmailValid(email) || !isPasswordValid(password)) return false;
        return repository.authenticate(email.trim(), password);
    }
}