package com.example.quick_cash.registration;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import androidx.annotation.NonNull;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

import java.util.Map;
import java.util.function.Consumer;

public class FirebaseCRUD {

    private final FirebaseAuth auth;
    private final DatabaseReference database;

    public FirebaseCRUD() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("users");
    }

    // Create user in Firebase Authentication
    public void createUser(String name, String email, String password,Consumer<AuthResult> onSuccess, Consumer<Exception> onFailure) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccess::accept)
                .addOnFailureListener(onFailure::accept);
    }

    //Save in Realtime DB
    public void saveUserData(String userId, Map<String, Object> userData) {
        database.child(userId).setValue(userData);
    }
}
