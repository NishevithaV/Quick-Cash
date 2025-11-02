package com.example.quick_cash.views.reset_password;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
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

    private boolean emailSent = false;

    /**
     * Overriden onCreate function to start activity, initialize UI, properties, and set listeners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reset_password);
        auth = FirebaseAuth.getInstance();
        emailValidator = new ResetPasswordValidator();

        initUI();
        initListeners();
    }

    /**
     * Init ui.
     */
    protected void initUI(){
        emailInput = findViewById(R.id.resetEmailInputID);
        resetButton = findViewById(R.id.resetButtonID);
        statusText = findViewById(R.id.resetPsswdStatusTextID);
        loginLink = findViewById(R.id.resetTologinLinkID);
    }

    /**
     * Init listeners.
     */
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
                Intent intent = new Intent(ResetPasswordActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (emailSent) {
                    Toast.makeText(ResetPasswordActivity.this, "Back is disabled", Toast.LENGTH_SHORT).show();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    private void sendResetEmail(){
        String email = emailInput.getText().toString().trim();
        if (emailValidator.isValidEmail(email)) {

            auth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            emailSent = true;
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
