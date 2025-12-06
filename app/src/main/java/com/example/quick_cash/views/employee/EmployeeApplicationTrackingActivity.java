package com.example.quick_cash.views.employee;

import android.app.Activity;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quick_cash.R;

public class EmployeeApplicationTrackingActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_application_tracking);

        RecyclerView rv = findViewById(R.id.recycler_applications);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // temp dummy adapter for UI - to be replaced later
        rv.setAdapter(new DummyApplicationAdapter());
    }
}
