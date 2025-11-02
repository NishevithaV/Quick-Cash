package com.example.quick_cash.registration;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import java.util.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;


import com.example.quick_cash.FirebaseCRUD.Users;
import com.example.quick_cash.R;
import com.example.quick_cash.login.LoginActivity;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    private EditText nameInput;
    private EditText emailInput;
    private EditText passwordInput;
    private Button employeeBtn;
    private Button employerBtn;
    private Button registerBtn;
    private Button loginBtn;
    private String userType = "";

    private Users firebaseCRUD;

    private Toast toast;
    public static String lastToastMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registration);

        initUI();
        firebaseCRUD = new Users(FirebaseDatabase.getInstance());

        // Initialize function to show toast
        toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        userType = "Employee"; // initial selection
        initListeners();
    }

    private void initListeners() {
        employeeBtn.setOnClickListener(view -> {
            // Employee clicked → only change if not already selected
            if(employeeBtn.getBackgroundTintList() == ColorStateList.valueOf(0xFFFFFFFF)){
                employeeBtn.setBackgroundTintList(ColorStateList.valueOf(0xFF1E64FF)); // blue
                employeeBtn.setTextColor(0xFFFFFFFF);
                employerBtn.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                employerBtn.setTextColor(0xFF1E64FF);

            }
            else{
                employeeBtn.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                employeeBtn.setTextColor(0xFF1E64FF);
                employerBtn.setBackgroundTintList(ColorStateList.valueOf(0xFF1E64FF));
                employerBtn.setTextColor(0xFFFFFFFF);

            }
        });

        employerBtn.setOnClickListener(view -> {
            // Employer clicked → only change if not already selected
            if(employerBtn.getBackgroundTintList() == ColorStateList.valueOf(0xFF1E64FF)){
                employerBtn.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                employerBtn.setTextColor(0xFF1E64FF);
                employeeBtn.setBackgroundTintList(ColorStateList.valueOf(0xFF1E64FF)); // blue
                employeeBtn.setTextColor(0xFFFFFFFF);
            }
            else{
                employerBtn.setBackgroundTintList(ColorStateList.valueOf(0xFF1E64FF));
                employerBtn.setTextColor(0xFFFFFFFF);
                employeeBtn.setBackgroundTintList(ColorStateList.valueOf(0xFFFFFFFF));
                employeeBtn.setTextColor(0xFF1E64FF);
            }
        });




        // After clicking registration button
        registerBtn.setOnClickListener(view -> {
            String name = nameInput.getText().toString().trim();
            String email = emailInput.getText().toString().trim();
            String password = passwordInput.getText().toString().trim();
            RegistrationValidator registrationValidator = new RegistrationValidator();

            if (!registrationValidator.validUser(userType)) {
                showToast(R.string.INVALID_USER_TYPE);
                return;
            }

            if (!registrationValidator.validName(name)) {
                showToast(R.string.INVALID_NAME);
                return;
            }

            if (!registrationValidator.validEmail(email)) {
                showToast(R.string.INVALID_EMAIL);
                return;
            }

            if (!registrationValidator.validPassword(password)) {
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
                        userData.put("password", password);

                        firebaseCRUD.saveUserData(userId, userData);
                    },
                    Throwable::printStackTrace
            );
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    private void initUI() {
        nameInput = findViewById(R.id.name_input);
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        employeeBtn = findViewById(R.id.employee_button);
        employerBtn = findViewById(R.id.employer_button);
        registerBtn = findViewById(R.id.register_button);
        loginBtn = findViewById(R.id.login_button);
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
