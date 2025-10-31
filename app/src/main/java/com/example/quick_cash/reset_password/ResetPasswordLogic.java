package com.example.quick_cash.reset_password;
import com.example.quick_cash.FirebaseCrud.ResetPasswordCRUD;

public class ResetPasswordLogic {
    private ResetPasswordCRUD crud;

    public ResetPasswordLogic() {
        crud = new ResetPasswordCRUD();
        crud.connectFirebase();
    }

    public ResetPasswordLogic(ResetPasswordCRUD injected) {
        crud = injected;
    }

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
                callback.onComplete(false, "invalid email");
            }
            return;
        }

        // AT-2: verify in existing Realtime DB
        crud.checkEmailExists(email, new ResetPasswordCRUD.BoolCallback() {
            @Override
            public void onResult(boolean exists) {
                if (!exists) {
                    if (callback != null) callback.onComplete(false, "invalid email");
                    return;
                }

                // AT-3: send the Firebase Auth reset email
                crud.sendResetEmail(email, new ResetPasswordCRUD.BoolCallback() {
                    @Override
                    public void onResult(boolean ok) {
                        if (callback != null) {
                            if (ok) {
                                callback.onComplete(true, "reset email sent!");
                            } else {
                                callback.onComplete(false, "invalid email");
                            }                        }
                    }
                });
            }
        });
    }

    public interface StatusCallback {
        void onComplete(boolean success, String message);
    }
}