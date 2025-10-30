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

        emailInput    = findViewById(R.id.email_input);
        passwordInput = findViewById(R.id.password_input);
        errorBox      = findViewById(R.id.error_box);
        errorTitle    = findViewById(R.id.error_title);
        errorMessage  = findViewById(R.id.error_message);
        loginButton   = findViewById(R.id.login_button);

        errorBox.setVisibility(View.GONE);

        loginButton.setOnClickListener(v -> {
            String email = safeText(emailInput);
            String pass  = safeText(passwordInput);

            if (!validator.isEmailValid(email)) {
                showError("Invalid Email", "Please enter a valid email address.");
                return;
            }
            if (!validator.isPasswordValid(pass)) {
                showError("Invalid Password", "Password must be at least 6 characters.");
                return;
            }

            boolean ok = validator.authenticate(email, pass);
            if (ok) {
                errorBox.setVisibility(View.GONE);
                Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();

                // TODO: navigate to the correct screen based on role
            } else {
                showError("Incorrect Login Details",
                        "The username or password you entered is incorrect. Please try again.");
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