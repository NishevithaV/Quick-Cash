package com.example.quick_cash.reset_password;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    // declaring the UI components
    private EditText emailInput;
    private Button resetButton;
    private TextView statusText;

    private FirebaseAuth auth;

    // Validator class
    private ResetPasswordValidator emailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reset_password);
        auth = FirebaseAuth.getInstance();

        emailInput = findViewById(R.id.emailInputID);
        resetButton = findViewById(R.id.resetButtonID);
        statusText = findViewById(R.id.statusTextID);
        emailValidator = new ResetPasswordValidator();

        resetButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String email = emailInput.getText().toString().trim();
                if (emailValidator.isValidEmail(email)) {

                    auth.sendPasswordResetEmail(email)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    statusText.setText(R.string.RESET_SEND_SUCCESSFUL);
                                    Toast.makeText(ResetPasswordActivity.this, R.string.RESET_SEND_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                                } else {
                                    statusText.setText(R.string.RESET_SEND_FAILED);
                                    Toast.makeText(ResetPasswordActivity.this, R.string.RESET_SEND_FAILED, Toast.LENGTH_SHORT).show();
                                }
                            });

                } else {
                    statusText.setText(R.string.INVALID_EMAIL);
                    Toast.makeText(ResetPasswordActivity.this, R.string.RESET_SEND_FAILED, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
