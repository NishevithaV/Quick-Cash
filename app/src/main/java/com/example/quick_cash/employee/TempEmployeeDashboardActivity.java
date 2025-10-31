package com.example.quick_cash.employee;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.quick_cash.R;

public class TempEmployeeDashboardActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Temporary layout in code (no XML needed)
        TextView textView = new TextView(this);
        textView.setText("Employee Dashboard Loaded Successfully");
        textView.setTextSize(20);
        setContentView(textView);
    }
}
