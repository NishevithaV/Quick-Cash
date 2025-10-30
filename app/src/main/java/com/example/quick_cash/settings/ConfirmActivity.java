package com.example.quick_cash.settings;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;

public class ConfirmActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button confirmButton;
    private Button cancelButton;
    private TextView roleSwitchText;
    private String currentUserEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm);

        // Get data from intent
        String currentRole = getIntent().getStringExtra("current_role");
        String newRole = getIntent().getStringExtra("new_role");
        currentUserEmail = getIntent().getStringExtra("current_email");

        // Initialize views
        roleSwitchText = findViewById(R.id.roleSwitchText);
        emailInput = findViewById(R.id.roleInputEmail);
        confirmButton = findViewById(R.id.roleConfirmButton);
        cancelButton = findViewById(R.id.roleCancelButton);

        // Set the role switch text
        roleSwitchText.setText(currentRole + " to " + newRole);

        // Set up click listeners
        confirmButton.setOnClickListener(v -> {
            String enteredEmail = emailInput.getText().toString().trim();
            if (enteredEmail.equals(currentUserEmail)) {
                // Email matches, proceed with role switch
                setResult(RESULT_OK);
                finish();
            } else {
                // Email doesn't match
                Toast.makeText(this, "Email does not match your current logged-in account",
                        Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> finish());
    }
}