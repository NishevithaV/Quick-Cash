package com.example.quick_cash.logout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import com.example.quick_cash.R;

public class SettingsActivity extends AppCompatActivity {
    private Button logoutButton;
    private LogoutHandler logoutHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        initUI();
        logoutHandler = new LogoutHandler(this);
        initListeners();
    }

    protected void initUI() {
        logoutButton = findViewById(R.id.logoutButton);
    }
    private void initListeners() {
        logoutButton.setOnClickListener(v -> logoutHandler.showLogoutConfirmation());
    }
}
