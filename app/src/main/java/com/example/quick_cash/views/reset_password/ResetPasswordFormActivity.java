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
import com.example.quick_cash.controllers.ResetPasswordValidator;
import com.example.quick_cash.utils.ResetHandler;
import com.example.quick_cash.views.login.LoginActivity;

public class ResetPasswordFormActivity extends AppCompatActivity {

    // declaring the UI components
    private String email;
    private EditText currPasswordField;
    private EditText newPasswordField;
    private Button resetButton;
    private TextView statusText;
    private TextView loginLink;
    private ResetHandler resetHandler;

    // Validator class
    private ResetPasswordValidator passwordValidator;

    private boolean passwdReset = false;

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
        this.setContentView(R.layout.activity_reset_form);
        passwordValidator = new ResetPasswordValidator();
        resetHandler = new ResetHandler();
        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        initUI();
        initListeners();
    }

    /**
     * Init ui.
     */
    protected void initUI(){
        currPasswordField = findViewById(R.id.currPasswd);
        newPasswordField = findViewById(R.id.newPasswd);
        resetButton = findViewById(R.id.formResetButtonID);
        loginLink = findViewById(R.id.resetTologinLinkID);
        statusText = findViewById(R.id.formResetPsswdStatusTextID);
    }

    /**
     * Init listeners.
     */
    protected void initListeners(){

        resetButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                resetPassword();
            }
        });

        loginLink.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent = new Intent(ResetPasswordFormActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (passwdReset) {
                    Toast.makeText(ResetPasswordFormActivity.this, "Back is disabled", Toast.LENGTH_SHORT).show();
                } else {
                    setEnabled(false);
                    getOnBackPressedDispatcher().onBackPressed();
                }
            }
        });
    }

    private void resetPassword(){
        String oldPassword = currPasswordField.getText().toString();
        String newPassword = newPasswordField.getText().toString();
        if (passwordValidator.isValidPassword(newPassword)) {
            resetHandler.reset(
                    email,
                    oldPassword,
                    newPassword,
                    new ResetHandler.PasswordChangeCallback() {
                        @Override
                        public void onSuccess() {
                            loginLink.setVisibility(android.view.View.VISIBLE);
                            passwdReset = true;
                            statusText.setText(R.string.RESET_SEND_SUCCESSFUL);
                            Toast.makeText(ResetPasswordFormActivity.this, R.string.RESET_SEND_SUCCESSFUL, Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String errorMessage) {
                            passwdReset = false;
                            statusText.setText(R.string.RESET_SEND_FAILED);
                            statusText.append(": "+errorMessage);
                            Toast.makeText(ResetPasswordFormActivity.this, R.string.RESET_SEND_FAILED, Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            statusText.setText(R.string.INVALID_PASSWORD);
            Toast.makeText(ResetPasswordFormActivity.this, R.string.INVALID_PASSWORD, Toast.LENGTH_SHORT).show();
        }
    }
}
