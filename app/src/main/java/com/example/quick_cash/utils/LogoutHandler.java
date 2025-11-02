package com.example.quick_cash.utils;


import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.quick_cash.views.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class LogoutHandler {
    private final Context context;
    private final FirebaseAuth auth;

    /**
     * Instantiates a new Logout handler.
     *
     * @param context the context
     */
    public LogoutHandler(Context context) {
        this.context = context;
        this.auth = FirebaseAuth.getInstance();
    }

    /**
     * Show logout confirmation.
     */
    public void showLogoutConfirmation() {
        AlertDialog.Builder logoutConfirmationPage = new AlertDialog.Builder(context);
        logoutConfirmationPage.setTitle("Confirm Logout");
        logoutConfirmationPage.setMessage("Are you sure you want to log out?");

        logoutConfirmationPage.setPositiveButton("Yes", (dialog, which) -> {
            signOut();
            Toast.makeText(context, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        });

        logoutConfirmationPage.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
        logoutConfirmationPage.create().show();
    }

    private void signOut() {
        auth.signOut();
    }
}
