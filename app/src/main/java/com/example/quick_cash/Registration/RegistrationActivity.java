package com.example.quick_cash.Registration;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.TextView;

import java.util.*;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name_input, email_input, password_input;
    private Button Employee_btn, Employer_btn, Register_btn, Login_btn;
    private TextView statusMessage;
    private String userType = "";

    private FirebaseCRUD firebaseCRUD;

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
        statusMessage = findViewById(R.id.statusMessage);
        firebaseCRUD = new FirebaseCRUD();

        // User type selection
        Employee_btn.setOnClickListener(view -> {
            userType = "Employee";
            statusMessage.setText("");
        });

        Employer_btn.setOnClickListener(view -> {
            userType = "Employer";
            statusMessage.setText("");
        });

        // After clciking registration button
        Register_btn.setOnClickListener(view -> {
            String name = name_input.getText().toString().trim();
            String email = email_input.getText().toString().trim();
            String password = password_input.getText().toString().trim();
            RegistrationValidator registrationValidator = new RegistrationValidator();



            if (!registrationValidator.ValidUser(userType)) {

                statusMessage.setText(R.string.INVALID_USER_TYPE);
                return;
            }

            if (!registrationValidator.ValidName(name)) {
                statusMessage.setText(R.string.INVALID_NAME);
                return;
            }

            if (!registrationValidator.ValidEmail(email)) {
                statusMessage.setText(R.string.INVALID_EMAIL);
                return;
            }

            if (!registrationValidator.ValidPassword(password)) {
                if (password.isEmpty()) {
                    statusMessage.setText(R.string.EMPTY_PASSWORD);
                } else {
                    statusMessage.setText(R.string.INVALID_PASSWORD);
                }
                return;
            }
            statusMessage.setText(R.string.REGISTRATION_SUCCESSFUL);


            firebaseCRUD.createUser(
                    name,
                    email,
                    password,
                    authResult -> {
                        // Success: store additional user info
                        String userId = authResult.getUser().getUid();
                        Map<String, Object> userData = new java.util.HashMap<>();
                        userData.put("name", name);
                        userData.put("email", email);
                        userData.put("userType", userType);
                        userData.put("userId", userId);

                        firebaseCRUD.saveUserData(userId, userData);
                    },
                    e -> {
                        // Failure
                        statusMessage.setText(R.string.REGISTRATION_FAILED);
                    }
            );
        });
    }

}
