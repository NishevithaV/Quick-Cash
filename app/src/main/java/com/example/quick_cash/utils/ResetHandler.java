package com.example.quick_cash.utils;

import android.text.style.TtsSpan;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quick_cash.controllers.ResetPasswordValidator;
import com.example.quick_cash.utils.FirebaseCRUD.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ResetHandler {

    private DatabaseReference usersRef;
    private FirebaseLoginHandler loginHandler;
    private ResetPasswordValidator psswdValidator;
    private FirebaseAuth auth;
    public ResetHandler() {
        usersRef = new Users(FirebaseDatabase.getInstance()).getUsersRef();
        auth = FirebaseAuth.getInstance();
        loginHandler = new FirebaseLoginHandler();
    }

    public interface EmailExistCallback {
        void onResult(boolean exists);
    }

    public void emailExists(String email, EmailExistCallback callback) {
        Query query = usersRef.orderByChild("email").equalTo(email);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exists = snapshot.exists();
                callback.onResult(exists);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onResult(false);
            }
        });
    }

    public interface PasswordChangeCallback {
        void onSuccess();
        void onFailure(String errorMessage);
    }

    public void reset(String email, String currPassword, String newPassword, PasswordChangeCallback callback) {
        auth.signInWithEmailAndPassword(email, currPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = auth.getCurrentUser();
                        if (currPassword.equals(newPassword)){
                            callback.onFailure("Same Password");
                        } else {
                            if (user != null) {
                                // Step 2: Update the password
                                user.updatePassword(newPassword)
                                        .addOnCompleteListener(updateTask -> {
                                            if (updateTask.isSuccessful()) {
                                                callback.onSuccess();
                                            } else {
                                                callback.onFailure(updateTask.getException() != null ?
                                                        updateTask.getException().getMessage() : "Failed to update password");
                                            }
                                        });
                                usersRef.child(user.getUid()).child("password").setValue(newPassword);
                                auth.signOut();
                            } else {
                                auth.signOut();
                                callback.onFailure("User not found after sign-in");
                            }
                        }
                    } else {
                        callback.onFailure(task.getException() != null ?
                                task.getException().getMessage() : "Authentication failed");
                    }
                });
    }
}
