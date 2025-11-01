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
import com.example.quick_cash.Registration.RegistrationActivity;
import com.example.quick_cash.employee.EmployeeDashboardActivity;
import com.example.quick_cash.employer.EmployerDashboardActivity;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * LoginActivity
 * Implements:
 *  - AT-1: Role-based dashboard redirection
 *  - AT-2: Error message box for invalid inputs or login errors
 *  - AT-3: Toast confirmation for success/failure of login attempt
 */
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

        // Redirect to registration page
        signupRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });

        // Login button logic
        loginButton.setOnClickListener(view -> {
            String email = safeText(emailInput);
            String pass = safeText(passwordInput);

            // Validation checks (AT-2)
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
                    FirebaseUser user = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser();
                    if (user != null) {
                        // AT-3 success toast
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();

                        // AT-1 role-based redirection
                        fetchUserRoleAndRedirect(user);
                    }
                } else {
                    // AT-3 failure toast
                    Toast.makeText(this,
                            "Login failed: Incorrect email or password.",
                            Toast.LENGTH_SHORT).show();

                    // AT-2 visual error box
                    showError("Incorrect Login Details",
                            "The username or password you entered is incorrect. Please try again.");
                }
            });
        });
    }

    // Fetch user role and redirect to corresponding dashboard (AT-1)
    private void fetchUserRoleAndRedirect(FirebaseUser user) {
        DatabaseReference userRef = FirebaseDatabase.getInstance()
                .getReference("users")
                .child(user.getUid())
                .child("userType");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists() && snapshot.getValue() != null) {
                    String role = snapshot.getValue(String.class);

                    if ("Employer".equalsIgnoreCase(role)) {
                        startActivity(new Intent(LoginActivity.this, EmployeeDashboardActivity.class));
                    } else if ("Employee".equalsIgnoreCase(role)) {
                        startActivity(new Intent(LoginActivity.this, EmployerDashboardActivity.class));
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

    // Safe text fetch utility
    private String safeText(EditText et) {
        return et.getText() == null ? "" : et.getText().toString().trim();
    }

    // Error box display (AT-2)
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