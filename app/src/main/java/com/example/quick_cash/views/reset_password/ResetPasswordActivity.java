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
import com.example.quick_cash.utils.ResetHandler;
import com.example.quick_cash.views.login.LoginActivity;
import com.example.quick_cash.controllers.ResetPasswordValidator;
import com.google.firebase.auth.FirebaseAuth;

/**
 * The type Reset password activity.
 */
public class ResetPasswordActivity extends AppCompatActivity {

    // declaring the UI components
    private EditText emailInput;
    private Button resetButton;
    private TextView statusText;
    private ResetHandler resetHandler;

    // Validator class
    private ResetPasswordValidator emailValidator;

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
        emailValidator = new ResetPasswordValidator();
        resetHandler = new ResetHandler();
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
    }

    /**
     * Init listeners.
     */
    protected void initListeners(){

        resetButton.setOnClickListener(v -> goToResetForm());
    }

    private void goToResetForm(){
        String email = emailInput.getText().toString().trim();
        if (emailValidator.isValidEmail(email)) {
            resetHandler.emailExists(email, exists -> {
                if (exists) {
                    Intent intent = new Intent(ResetPasswordActivity.this, ResetPasswordFormActivity.class);
                    intent.putExtra("email", email);
                    startActivity(intent);
                } else {
                    statusText.setText(R.string.EMAIL_NOT_EXIST);
                    Toast.makeText(ResetPasswordActivity.this, R.string.EMAIL_NOT_EXIST, Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            statusText.setText(R.string.INVALID_EMAIL);
            Toast.makeText(ResetPasswordActivity.this, R.string.INVALID_EMAIL, Toast.LENGTH_SHORT).show();
        }
    }
}
