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

        roleSwitchText = findViewById(R.id.roleSwitchText);
        emailInput = findViewById(R.id.roleInputEmail);
        confirmButton = findViewById(R.id.roleConfirmButton);
        cancelButton = findViewById(R.id.roleCancelButton);

        

        cancelButton.setOnClickListener(v -> finish());
    }
}