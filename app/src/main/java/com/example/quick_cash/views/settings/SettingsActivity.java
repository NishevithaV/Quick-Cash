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
 * The type Settings activity.
 */
public class SettingsActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button switchRoleButton;
    private Button resetPsswdButton;
    private LogoutHandler logoutHandler;
    private SwitchRoleHandler switchRoleHandler;

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
        setContentView(R.layout.activity_settings);

        initUI();
        initHandlers();
        initListeners();
    }

    /**
     * Init ui.
     */
    public void initUI() {
        logoutButton = findViewById(R.id.logoutButton);
        switchRoleButton = findViewById(R.id.switchRoleButton);
        resetPsswdButton = findViewById(R.id.resetPasswordButton);
    }

    private void initHandlers() {
        logoutHandler = new LogoutHandler(this);
        switchRoleHandler = new SwitchRoleHandler(this);
    }

    private void initListeners() {
        logoutButton.setOnClickListener(v -> logoutHandler.showLogoutConfirmation());
        switchRoleButton.setOnClickListener(v -> switchRoleHandler.handleSwitchRole());
        resetPsswdButton.setOnClickListener(v -> startActivity(new Intent(SettingsActivity.this, ResetPasswordActivity.class)));
    }

    /**
     * Gets switch role handler.
     *
     * @return the switch role handler
     */
    public SwitchRoleHandler getSwitchRoleHandler() {
        return switchRoleHandler;
    }
}

