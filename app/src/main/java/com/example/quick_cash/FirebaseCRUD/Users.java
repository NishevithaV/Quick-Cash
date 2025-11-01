package com.example.quick_cash.FirebaseCRUD;

import androidx.annotation.NonNull;

import com.example.quick_cash.Models.Job;
import com.example.quick_cash.Models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    public interface UsersCallback {
        void onCallback(User user);
    }

    public void getUser(String userID, UsersCallback callback){
        final User[] userArr = new User[1];
        usersRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArr[0] = new User(
                        String.valueOf(snapshot.child("userType").getValue(String.class)),
                        String.valueOf(snapshot.child("email").getValue(String.class)),
                        String.valueOf(snapshot.child("name").getValue(String.class)),
                        String.valueOf(snapshot.child("userId").getValue(String.class))
                );
                callback.onCallback(userArr[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });
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
