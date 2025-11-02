package com.example.quick_cash.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.Task;

public class FirebaseLoginHandler {

    private final FirebaseAuth auth = FirebaseAuth.getInstance();
    private final DatabaseReference dbRef =
            FirebaseDatabase.getInstance().getReference("users");

    /**
     * Sign in task.
     *
     * @param email    the email
     * @param password the password
     * @return the task
     */
    public Task<FirebaseUser> signIn(String email, String password) {
        return auth.signInWithEmailAndPassword(email, password)
                .continueWith(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (user != null) {
                            dbRef.child(user.getUid()).child("lastLogin")
                                    .setValue(System.currentTimeMillis());
                        }
                        return user;
                    } else {
                        throw task.getException();
                    }
                });
    }
}