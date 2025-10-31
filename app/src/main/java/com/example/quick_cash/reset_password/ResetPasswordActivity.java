package com.example.quick_cash.reset_password;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quick_cash.R;

public class ResetPasswordActivity extends AppCompatActivity {

    // declaring the UI components
    private EditText emailInput;
    private Button resetButton;
    private TextView statusText;

    // logic class
    private ResetPasswordLogic logic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_reset_password);

        emailInput = findViewById(R.id.emailInputID);
        resetButton = findViewById(R.id.resetButtonID);
        statusText = findViewById(R.id.statusTextID);
        logic = new ResetPasswordLogic();

        resetButton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                String email = emailInput.getText().toString().trim();

                logic.sendResetLink(email, new ResetPasswordLogic.StatusCallback() {
                    @Override
                    public void onComplete(boolean success, String message) {

                        // displaying message visibly for Espresso test
                        statusText.setText(message);
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
