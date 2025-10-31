package com.example.quick_cash.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.switchrole.ConfirmActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SettingsActivity1 extends AppCompatActivity {

    private Button switchRoleButton;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference usersRef;
    private String currentRole;

    // Allow dependency injection for testing
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
        }
        return mDatabase;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        
        // Initialize Firebase
        mAuth = getFirebaseAuth();
        mDatabase = getFirebaseDatabase();
        usersRef = mDatabase.getReference("users");
        
        switchRoleButton = findViewById(R.id.switchRoleButton);
        
        // Set up click listener for switch role button
        switchRoleButton.setOnClickListener(v -> {
            FirebaseUser currentUser = getFirebaseAuth().getCurrentUser();
            if (currentUser != null) {
                // Fetch current user's role from Firebase
                String userId = currentUser.getUid();
                usersRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                            currentRole = dataSnapshot.child("type").getValue(String.class);
                            String newRole = currentRole.equalsIgnoreCase("employer") ? "employee" : "employer";
                            
                            Intent intent = new Intent(SettingsActivity1.this, ConfirmActivity.class);
                            intent.putExtra("current_role", currentRole);
                            intent.putExtra("new_role", newRole);
                            intent.putExtra("current_email", currentUser.getEmail());
                            intent.putExtra("user_id", userId);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SettingsActivity1.this, "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(SettingsActivity1.this, "Error fetching user data", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "No user logged in", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
