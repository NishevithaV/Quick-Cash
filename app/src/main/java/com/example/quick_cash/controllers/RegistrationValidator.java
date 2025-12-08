package com.example.quick_cash.controllers;

/**
 * The type Registration validator.
 */
public class RegistrationValidator {

    /**
     * Instantiates a new Registration validator.
     */
    public RegistrationValidator(){
        // Default Constructor
    }

    /**
     * Valid user boolean.
     *
     * @param userType the user type
     * @return the boolean
     */
    public boolean validUser(String userType) {
        if (userType == null ||userType.isEmpty() ) return false;
        return userType.equals("Employee") || userType.equals("Employer");
    }

    /**
     * Valid name boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean validName(String name) {
        return !(name == null || name.trim().isEmpty());
    }

    /**
     * Valid email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public boolean validEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    /**
     * Valid password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public boolean validPassword(String password) {
        if(password == null) return false;
        if (password.isEmpty()) return false;
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(regex);
    }
}
