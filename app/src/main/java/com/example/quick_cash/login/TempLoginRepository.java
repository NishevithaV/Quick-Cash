package com.example.quick_cash.login;

/**
 * Mock repository used for local testing
 * Will be replaced with Firebase implementation
 */
public class TempLoginRepository implements LoginRepository {

    private static final String DEMO_EMAIL = "user@example.com";
    private static final String DEMO_PASS  = "abc123";

    @Override
    public boolean authenticate(String email, String password) {
        return DEMO_EMAIL.equalsIgnoreCase(email) && DEMO_PASS.equals(password);
    }
}