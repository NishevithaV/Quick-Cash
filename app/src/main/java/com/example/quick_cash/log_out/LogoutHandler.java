package com.example.quick_cash.log_out;

import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.auth.FirebaseAuth;

public class LogoutHandler {
    private final Context context;
    private final FirebaseAuth auth;

    public LogoutHandler(Context context) {
        this.context = context;
        this.auth = FirebaseAuth.getInstance();
    }

    public void showLogoutConfirmation() {
        AlertDialog.Builder logoutConfirmationPage = new AlertDialog.Builder(context);
        logoutConfirmationPage.setTitle("Confirm Logout");
        logoutConfirmationPage.setMessage("Are you sure you want to log out?");

        logoutConfirmationPage.setPositiveButton("Yes", (dialog, which) -> {
            signOut();
            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show();
        });

        logoutConfirmationPage.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        logoutConfirmationPage.create().show();
    }

    private void signOut() {
        auth.signOut();
    }
}
