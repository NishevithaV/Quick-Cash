package com.example.quick_cash.views.employee;

import static android.view.View.GONE;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quick_cash.R;
import com.example.quick_cash.views.maps.CurrentLocationActivity;
import com.example.quick_cash.views.settings.SettingsActivity;
import com.google.firebase.messaging.FirebaseMessaging;

public class EmployeeDashboardActivity extends AppCompatActivity {

    Button btnFindJobs;
    Button btnSettings;

    Button btnNearbyJobs;
    Button btnMyApps;


    /**
     *
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_employee_dashboard);
        initUI();
        Intent intent = getIntent();
        if (!intent.getBooleanExtra("isTest", false)) {
            // Request notifications permission
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1001);
            }
        }
        FirebaseMessaging.getInstance().subscribeToTopic("nearbyJobs");
        initListeners();

        // Check if we should show the success message
        if (getIntent().getBooleanExtra("show_success_message", false)) {
            Toast.makeText(this, "Role switched successfully", Toast.LENGTH_SHORT).show();
        }
    }


    private void initListeners() {
        // Navigate to Find Jobs screen
        btnFindJobs.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeeDashboardActivity.this, JobSearchActivity.class);
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent( EmployeeDashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });

        btnNearbyJobs.setOnClickListener(v -> {
            Intent intent = new Intent( EmployeeDashboardActivity.this, CurrentLocationActivity.class);
            startActivity(intent);
        });
        btnNearbyJobs.setVisibility(GONE);
        btnMyApps.setOnClickListener(v -> {
            Intent intent = new Intent( EmployeeDashboardActivity.this, EmployeeApplicationsActivity.class);
            startActivity(intent);
        });
    }

    private void initUI() {
        btnFindJobs = findViewById(R.id.btnFindJobs);
        btnSettings = findViewById(R.id.btnSettings);
        btnNearbyJobs = findViewById(R.id.btnNearbyJobs);
        btnMyApps = findViewById(R.id.btnMyApps);
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
