package com.example.quick_cash.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.employee.TempEmployeeDashboardActivity;
import com.example.quick_cash.job_posting.TempEmployerDashboardActivity;
import com.example.quick_cash.registration.TempRegistrationActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput;
    private LinearLayout errorBox;
    private TextView errorTitle, errorMessage;
    private Button loginButton;

    private final LoginValidator validator = new LoginValidator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Initialize views
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        errorBox = findViewById(R.id.error_box);
        errorTitle = findViewById(R.id.error_title);
        errorMessage = findViewById(R.id.error_message);
        loginButton = findViewById(R.id.login_button);
        TextView signupRedirect = findViewById(R.id.signup_redirect);

        errorBox.setVisibility(View.GONE);

        FirebaseLoginRepository repo = new FirebaseLoginRepository();

        signupRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, TempRegistrationActivity.class);
            startActivity(intent);
        });

        loginButton.setOnClickListener(view -> {
            String email = safeText(emailInput);
            String pass = safeText(passwordInput);

            if (!validator.isEmailValid(email)) {
                showError("Invalid Email", "Please enter a valid email address.");
                return;
            }

            if (!validator.isPasswordValid(pass)) {
                showError("Invalid Password", "Password must be at least 6 characters.");
                return;
            }

            repo.signIn(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult();
                    if (user != null) {
                        Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
                        fetchUserRoleAndRedirect(user);
                    }
                } else {
                    showError("Incorrect Login Details",
                            "The username or password you entered is incorrect. Please try again.");
                }
            });
        });
    }

    private void fetchUserRoleAndRedirect(FirebaseUser user) {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getUid())
                .child("userType");

        Toast.makeText(this, "Fetching userType for UID: " + user.getUid(), Toast.LENGTH_SHORT).show();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue() != null) {
                    String role = snapshot.getValue(String.class);
                    Toast.makeText(LoginActivity.this, "Fetched role: " + role, Toast.LENGTH_SHORT).show();

                    if ("Employer".equalsIgnoreCase(role)) {
                        Intent intent = new Intent(LoginActivity.this, TempEmployerDashboardActivity.class);
                        startActivity(intent);
                    } else if ("Employee".equalsIgnoreCase(role)) {
                        Intent intent = new Intent(LoginActivity.this, TempEmployeeDashboardActivity.class);
                        startActivity(intent);
                    } else {
                        showError("Error", "Unknown userType: " + role);
                    }
                } else {
                    showError("Error", "User not found in database. Please register first.");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                showError("Database Error", "Failed to fetch userType: " + error.getMessage());
            }
        });
    }

    private String safeText(EditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    private void showError(String title, String message) {
        errorTitle.setText(title);
        errorMessage.setText(message);
        if (errorBox.getVisibility() != View.VISIBLE) {
            errorBox.setAlpha(0f);
            errorBox.setVisibility(View.VISIBLE);
            errorBox.animate().alpha(1f).setDuration(250).start();
        }
    }
}