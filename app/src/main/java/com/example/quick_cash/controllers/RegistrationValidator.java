package com.example.quick_cash.controllers;

public class RegistrationValidator {

    public RegistrationValidator(){
        // Default Constructor
    }

    public boolean validUser(String userType) {
        if (userType == null ||userType.isEmpty() ) return false;
        return userType.equals("Employee") || userType.equals("Employer");
    }

    public boolean validName(String name) {
        return !(name == null || name.trim().isEmpty());
    }

    public boolean validEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return email.matches(emailRegex);
    }

    public boolean validPassword(String password) {
        if(password == null) return false;
        if (password.isEmpty()) return false;
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(regex);
    }
}
