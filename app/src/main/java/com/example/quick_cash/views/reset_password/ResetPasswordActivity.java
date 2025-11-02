package com.example.quick_cash.views.reset_password;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.views.login.LoginActivity;
import com.example.quick_cash.controllers.ResetPasswordValidator;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    // declaring the UI components
    private EditText emailInput;
    private Button resetButton;
    private TextView statusText;
    private TextView loginLink;

    private FirebaseAuth auth;

    // Validator class
    private ResetPasswordValidator emailValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reset_password);
        auth = FirebaseAuth.getInstance();
        emailValidator = new ResetPasswordValidator();

        initUI();
        initListeners();
    }

    protected void initUI(){
        emailInput = findViewById(R.id.resetEmailInputID);
        resetButton = findViewById(R.id.resetButtonID);
        statusText = findViewById(R.id.resetPsswdStatusTextID);
        loginLink = findViewById(R.id.resetTologinLinkID);
    }

    protected void initListeners(){

        resetButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                sendResetEmail();
            }
        });

        loginLink.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
            }
        });
    }

    private void sendResetEmail(){
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
            loginLink.setVisibility(android.view.View.VISIBLE);
        } else {
            statusText.setText(R.string.INVALID_EMAIL);
            Toast.makeText(ResetPasswordActivity.this, R.string.RESET_SEND_FAILED, Toast.LENGTH_SHORT).show();
        }
    }
}
