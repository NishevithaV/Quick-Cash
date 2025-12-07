package com.example.quick_cash.controllers;
import com.example.quick_cash.controllers.RegistrationValidator;

/**
 * The type Reset password validator.
 */
public class ResetPasswordValidator {

    private RegistrationValidator psswdValidator;

    /**
     * Instantiates a new Reset password validator.
     */
    public ResetPasswordValidator() {
        psswdValidator = new RegistrationValidator();
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

    /**
     * Is valid password boolean.
     *
     * @param psswd the psswd
     * @return the boolean
     */
    public boolean isValidPassword(String psswd) {
        return psswdValidator.validPassword(psswd);
    }
}