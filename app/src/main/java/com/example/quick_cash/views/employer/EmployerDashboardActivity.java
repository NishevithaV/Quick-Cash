package com.example.quick_cash.views.employer;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.views.settings.SettingsActivity;

public class EmployerDashboardActivity extends AppCompatActivity {

    Button btnViewListings;
    Button btnReviewApps;
    Button btnSettings;

    Button btnPostJobs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_employer_dashboard);
        initUI();
        // Check if we should show the success message
        if (getIntent().getBooleanExtra("show_success_message", false)) {
            Toast.makeText(this, "Role switched successfully", Toast.LENGTH_SHORT).show();
        }
        initListeners();
    }

    private void initListeners() {
        btnViewListings.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerDashboardActivity.this, EmployerListingsActivity.class);
            startActivity(intent);
        });

        btnPostJobs.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerDashboardActivity.this, PostFormActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent( EmployerDashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        btnReviewApps.setOnClickListener(v -> {
            Intent intent = new Intent( EmployerDashboardActivity.this, ApplicationsActivity.class);
            startActivity(intent);
        });
    }

    private void initUI() {
        btnViewListings = findViewById(R.id.btnViewListings);
        btnReviewApps = findViewById(R.id.btnReviewApps);
        btnPostJobs = findViewById(R.id.btnPostJobs);
        btnSettings = findViewById(R.id.btnSettingsEmployer);
    }
}
