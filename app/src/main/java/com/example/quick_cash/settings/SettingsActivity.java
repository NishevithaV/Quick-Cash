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
        
        
    }
}
