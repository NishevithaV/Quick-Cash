package com.example.quick_cash.job_search;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;

public class JobSearchActivity extends AppCompatActivity {

    EditText userSearch;
    TextView resultsView;
    Spinner categorySelector;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_job_list);
        initUI();
    }

    protected void initUI() {
        this.userSearch = findViewById(R.id.userSearch);
        this.resultsView = findViewById(R.id.resultsView);
        this.categorySelector = findViewById(R.id.catSelect);
    }
}
