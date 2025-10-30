package com.example.quick_cash.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.google.firebase.auth.FirebaseUser;

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

        // Initialize all views
        emailInput = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        errorBox = findViewById(R.id.error_box);
        errorTitle = findViewById(R.id.error_title);
        errorMessage = findViewById(R.id.error_message);
        loginButton = findViewById(R.id.login_button);

        errorBox.setVisibility(View.GONE);

        FirebaseLoginRepository repo = new FirebaseLoginRepository();

        // Handle button click
        loginButton.setOnClickListener(v -> {
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

            // Firebase login
            repo.signIn(email, pass).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = task.getResult();
                    Toast.makeText(this, "Welcome " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    errorBox.setVisibility(View.GONE);
                    // TODO: navigate to next screen
                } else {
                    showError("Incorrect Login Details",
                            "The username or password you entered is incorrect. Please try again.");
                }
            });
        });
    }

    // also need to handle redirecting back to sign up page

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
