package com.example.quick_cash.controllers;

public class ResetPasswordValidator {

    /**
     * Instantiates a new Reset password validator.
     */
    public ResetPasswordValidator() {
        // Default Constructor
    }

    /**
     * Is valid email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        email = email.trim();   //incase any spaces
        boolean validEmail = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]+$");
        return validEmail;
    }
}