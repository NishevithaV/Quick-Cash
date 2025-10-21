package com.example.quick_cash.job_search;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;

public class JobSearchActivity extends AppCompatActivity {

    EditText userSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_job_list);
    }

    protected void initUI() {
        this.userSearch = findViewById(R.id.userSearch);
    }
}
