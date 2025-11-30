package com.example.quick_cash.views.employee;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;


public class EmployeeApplicationsActivity extends AppCompatActivity {

    ListView appsListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_employee_application_tracking);
        initUI();
        initListeners();
    }

    private void initUI() {
        appsListView = findViewById(R.id.recycler_applications);
    }

    private void initListeners() {
    }
}
