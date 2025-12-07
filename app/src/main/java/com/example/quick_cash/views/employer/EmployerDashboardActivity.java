package com.example.quick_cash.views.employer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quick_cash.R;
import com.example.quick_cash.views.settings.SettingsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

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
        Intent intent = getIntent();
        if (!intent.getBooleanExtra("isTest", false)) {
            // Request notifications permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }

        // Check if we should show the success message
        if (getIntent().getBooleanExtra("show_success_message", false)) {
            Toast.makeText(this, "Role switched successfully", Toast.LENGTH_SHORT).show();
        }
        FirebaseMessaging.getInstance().unsubscribeFromTopic("nearbyJobs");
        initListeners();
    }

    private void initListeners() {
        btnViewListings.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerDashboardActivity.this, EmployerListingsActivity.class);
            String uid = FirebaseAuth.getInstance().getCurrentUser() != null
                    ? FirebaseAuth.getInstance().getCurrentUser().getUid()
                    : "testUserID";
            intent.putExtra("userID", uid);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("Permissions", "Notifications permission granted");
            } else {
                Log.d("Permissions", "Notifications permission denied");
            }
        }
    }
}
