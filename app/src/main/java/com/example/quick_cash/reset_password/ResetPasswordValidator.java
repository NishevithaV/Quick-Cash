package com.example.quick_cash.reset_password;

public class ResetPasswordValidator {

    public ResetPasswordValidator() {

    }
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        email = email.trim();   //incase any spaces
        boolean validEmail = email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-zA-Z]+$");
        return validEmail;
    }
}