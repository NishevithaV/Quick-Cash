package com.example.quick_cash.views.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.LogoutHandler;
import com.example.quick_cash.views.reset_password.ResetPasswordActivity;
import com.example.quick_cash.utils.SwitchRoleHandler;

/**
 * Settings screen that allows users to log out or switch roles.
 */
public class SettingsActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button switchRoleButton;
    private Button resetPsswdButton;
    private LogoutHandler logoutHandler;
    private SwitchRoleHandler switchRoleHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        initUI();
        initHandlers();
        initListeners();
    }

    /**
     * Initialize UI elements from the layout.
     */
    public void initUI() {
        logoutButton = findViewById(R.id.logoutButton);
        switchRoleButton = findViewById(R.id.switchRoleButton);
        resetPsswdButton = findViewById(R.id.resetPasswordButton);
    }

    /**
     * Initialize helper handler classes.
     */
    private void initHandlers() {
        logoutHandler = new LogoutHandler(this);
        switchRoleHandler = new SwitchRoleHandler(this);
    }

    /**
     * Set up event listeners for buttons.
     */
    private void initListeners() {
        logoutButton.setOnClickListener(v -> logoutHandler.showLogoutConfirmation());
        switchRoleButton.setOnClickListener(v -> switchRoleHandler.handleSwitchRole());
        resetPsswdButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, ResetPasswordActivity.class)));
    }

    public SwitchRoleHandler getSwitchRoleHandler() {
        return switchRoleHandler;
    }
}

