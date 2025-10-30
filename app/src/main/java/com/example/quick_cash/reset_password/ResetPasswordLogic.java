package com.example.quick_cash.reset_password;

public class ResetPasswordLogic {
    public boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        email = email.trim();   //incase any spaces
        boolean validEmail = email.contains("@") && email.contains(".");
        return validEmail;
    }

    public void sendResetLink(final String email, final StatusCallback callback) {
        if (!isValidEmail(email)) {
            if (callback != null) {
                callback.onComplete(false, "Invalid Email!");
            }
            return;
        }
        if (callback != null) {
            callback.onComplete(true, "Reset Email sent!");
        }
    }

    public interface StatusCallback {
        void onComplete(boolean success, String message);
    }
}