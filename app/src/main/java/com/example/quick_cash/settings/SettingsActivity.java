package com.example.quick_cash.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.Models.User;
import com.example.quick_cash.R;

public class SettingsActivity extends AppCompatActivity {

    private Button switchRoleButton;
    private User currentUser; // Assume this is set somewhere when user logs in

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        
        switchRoleButton = findViewById(R.id.switchRoleButton);
        
        // Set up click listener for switch role button
        switchRoleButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, ConfirmActivity.class);
            // Pass current role info to confirm activity
            intent.putExtra("current_role", currentUser.getType());
            intent.putExtra("new_role", currentUser.getType().equals("employer") ? "employee" : "employer");
            intent.putExtra("current_email", currentUser.getEmail());
            startActivity(intent);
        });
    }
}
