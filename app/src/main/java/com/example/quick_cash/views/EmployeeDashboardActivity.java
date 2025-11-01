package com.example.quick_cash.views;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.job_search.JobSearchActivity;

public class EmployeeDashboardActivity extends AppCompatActivity {

    Button btnFindJobs;
    Button btnSettings;

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
        this.setContentView(R.layout.activity_employee_dash);
        initUI();
        initListeners();
    }

    private void initListeners() {
        // Navigate to Find Jobs screen
        btnFindJobs.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeeDashboardActivity.this, JobSearchActivity.class);
            startActivity(intent);
        });
    }

    private void initUI() {
        btnFindJobs = findViewById(R.id.btnFindJobs);
        btnSettings = findViewById(R.id.btnSettings);
    }

}
