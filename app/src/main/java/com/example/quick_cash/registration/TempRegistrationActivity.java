package com.example.quick_cash.registration;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class TempRegistrationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Simple placeholder layout
        TextView tv = new TextView(this);
        tv.setText("✅ Temporary Registration Page Loaded Successfully");
        tv.setTextSize(20);
        setContentView(tv);
    }
}