package com.example.quick_cash.registration;

public class RegistrationValidator {
    private String userType = "";
    private String name = "";
    private String email = "";
    private String password = "";

    public RegistrationValidator(){
        this.userType = "";
        this.name = "";
        this.email = "";
        this.password = "";


    }

    public boolean ValidUser(String userType) {
        if (userType == null ||userType.isEmpty() ) return false;
        return userType.equals("Employee") || userType.equals("Employer");
    }

    public boolean ValidName(String name) {
        if( name ==null || name.trim().isEmpty() ) return false;
        return true;
    }

    public boolean ValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) return false;
        return true;
    }

    public boolean ValidPassword(String password) {
        if(password == null) return false;
        if (password.isEmpty()) return false;
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#$%^&+=!]).{8,}$";
        return password.matches(regex);
    }
}
