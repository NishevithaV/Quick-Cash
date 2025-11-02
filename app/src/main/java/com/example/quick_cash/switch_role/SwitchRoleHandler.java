package com.example.quick_cash.switch_role;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Handles logic for switching user roles (employer <-> employee).
 * Supports dependency injection for Firebase services to make testing easier.
 */
public class SwitchRoleHandler {

    private final Context context;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersRef;

    public SwitchRoleHandler(Context context) {
        this.context = context;
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.usersRef = mDatabase.getReference("users");
    }

    // ===== Dependency Injection =====
    public void setFirebaseAuth(FirebaseAuth auth) {
        this.mAuth = auth;
    }

    public void setFirebaseDatabase(FirebaseDatabase database) {
        this.mDatabase = database;
        this.usersRef = database.getReference("users");
    }

    protected FirebaseAuth getFirebaseAuth() {
        if (mAuth == null) {
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth;
    }

    protected FirebaseDatabase getFirebaseDatabase() {
        if (mDatabase == null) {
            mDatabase = FirebaseDatabase.getInstance();
            usersRef = mDatabase.getReference("users");
        }
        return mDatabase;
    }

    // ===== Core Logic =====
    public void handleSwitchRole() {
        FirebaseUser currentUser = getFirebaseAuth().getCurrentUser();
        String tag = "SwitchRoleHandler";
        if (currentUser == null) {
            Toast.makeText(context, "No user logged in", Toast.LENGTH_SHORT).show();
            android.util.Log.e(tag, "No user logged in");
            return;
        }

        String userId = currentUser.getUid();
        String userEmail = currentUser.getEmail();
        android.util.Log.d(tag, "User ID: " + userId + ", Email: " + userEmail);
        
        usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                android.util.Log.d(tag, "DataSnapshot exists: " + dataSnapshot.exists());
                android.util.Log.d(tag, "DataSnapshot: " + dataSnapshot.toString());
                
                if (!dataSnapshot.exists()) {
                    Toast.makeText(context, "User data not found", Toast.LENGTH_SHORT).show();
                    android.util.Log.e(tag, "User data not found in Firebase");
                    return;
                }

                String currentRole = dataSnapshot.child("userType").getValue(String.class);
                android.util.Log.d(tag, "Current role: " + currentRole);
                
                if (currentRole == null) {
                    Toast.makeText(context, "User role not found", Toast.LENGTH_SHORT).show();
                    android.util.Log.e(tag, "User role is null");
                    return;
                }

                // Preserve the capitalization from Firebase
                String newRole = currentRole.equalsIgnoreCase("employer") ? "Employee" : "Employer";
                android.util.Log.d(tag, "Switching from " + currentRole + " to " + newRole);

                Intent intent = new Intent(context, ConfirmActivity.class);
                intent.putExtra("current_role", currentRole);
                intent.putExtra("new_role", newRole);
                intent.putExtra("current_email", currentUser.getEmail());
                intent.putExtra("user_id", userId);

                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "Error fetching user data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


