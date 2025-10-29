package com.example.quick_cash.Registration;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.quick_cash.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name_input, email_input, password_input;
    private Button Employee_btn, Employer_btn, Register_btn, Login_btn;
    private String userType = "";

    private FirebaseCRUD firebaseCRUD;

    private Toast toast;
    public static String lastToastMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        name_input = findViewById(R.id.name_input);
        email_input = findViewById(R.id.email_input);
        password_input = findViewById(R.id.password_input);
        Employee_btn = findViewById(R.id.employee_button);
        Employer_btn = findViewById(R.id.employer_button);
        Register_btn = findViewById(R.id.register_button);
        Login_btn = findViewById(R.id.login_button);
        firebaseCRUD = new FirebaseCRUD();

        // Initialize function to show toast
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        // User type selection
        Employee_btn.setOnClickListener(view -> {
            userType = "Employee";
        });

        Employer_btn.setOnClickListener(view -> {
            userType = "Employer";
        });

        // After clicking registration button
        Register_btn.setOnClickListener(view -> {
            String name = name_input.getText().toString().trim();
            String email = email_input.getText().toString().trim();
            String password = password_input.getText().toString().trim();
            RegistrationValidator registrationValidator = new RegistrationValidator();

            if (!registrationValidator.ValidUser(userType)) {
                showToast(R.string.INVALID_USER_TYPE);
                return;
            }

            if (!registrationValidator.ValidName(name)) {
                showToast(R.string.INVALID_NAME);
                return;
            }

            if (!registrationValidator.ValidEmail(email)) {
                showToast(R.string.INVALID_EMAIL);
                return;
            }

            if (!registrationValidator.ValidPassword(password)) {
                if (password.isEmpty()) {
                    showToast(R.string.EMPTY_PASSWORD);
                } else {
                    showToast(R.string.INVALID_PASSWORD);
                }
                return;
            }

            showToast(R.string.REGISTRATION_SUCCESSFUL);

            firebaseCRUD.createUser(
                    name,
                    email,
                    password,
                    authResult -> {
                        String userId = authResult.getUser().getUid();
                        Map<String, Object> userData = new java.util.HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);
                        userData.put("userType", userType);
                        userData.put("userId", userId);

                        firebaseCRUD.saveUserData(userId, userData);
                    },
                    e -> {
                        e.printStackTrace();
                    }
            );
        });
    }

    // Unified toast helper method
    private void showToast(String message) {
        toast.setText(message);
        toast.show();
    }

    private void showToast(int resId) {
        String msg = getString(resId);
        lastToastMessage = msg;
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
