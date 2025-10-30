package com.example.quick_cash.login;

/**
 * Interface defining authentication operations for the Login feature.
 */
public interface LoginRepository {
    boolean authenticate(String email, String password);
}
