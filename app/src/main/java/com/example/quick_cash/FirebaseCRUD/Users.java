package com.example.quick_cash.FirebaseCRUD;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Users {
    private final FirebaseDatabase database;
    private DatabaseReference usersRef;

    public Users(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.initializeRefListeners();
    }

    private void initializeDatabaseRefs() {
        this.usersRef = this.database.getReference("users");
    }

    private void initializeRefListeners() {
        this.setUsersListener();
    }

    private void setUsersListener(){
    }

    public boolean postUser(Map<String, String> job){
        return false;
    }

    public Map<String, String> getUser(){
        return null;
    }

    // Interface for role update callback
    public interface RoleUpdateCallback {
        void onSuccess();
        void onFailure(String error);
    }

    // Method to update user role in Firebase
    public void updateUserRole(String userId, String newRole, RoleUpdateCallback callback) {
        usersRef.child(userId).child("type").setValue(newRole)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}
