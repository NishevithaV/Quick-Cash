package com.example.quick_cash.employee;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.quick_cash.R;
import com.example.quick_cash.settings.SettingsActivity;

public class EmployeeDashboardActivity extends AppCompatActivity {

    private Button settingsPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_employee_dashboard);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        settingsPageButton = findViewById(R.id.settingsPageEmployeeButton);

        settingsPageButton.setOnClickListener(v -> {
            Intent intent = new Intent(EmployeeDashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        // Check if we should show the success message
        if (getIntent().getBooleanExtra("show_success_message", false)) {
            Toast.makeText(this, "Role switched successfully", Toast.LENGTH_SHORT).show();
        }
    }
}