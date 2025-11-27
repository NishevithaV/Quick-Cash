package com.example.quick_cash.views.employer;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;

public class EmployerDashboardActivity extends AppCompatActivity {

    Button btnViewListings;
    Button btnReviewApps;
    Button btnSettings;

    Button btnPostJobs;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_employer_dashboard);
        initUI();
        initListeners();
    }

    private void initListeners() {
    }

    private void initUI() {
        btnViewListings = findViewById(R.id.btnViewListings);
        btnReviewApps = findViewById(R.id.btnReviewApps);
        btnPostJobs = findViewById(R.id.btnPostJobs);
        btnSettings = findViewById(R.id.btnSettingsEmployer);
    }
}
