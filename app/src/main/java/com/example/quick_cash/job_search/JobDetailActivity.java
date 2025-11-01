package com.example.quick_cash.job_search;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;

public class JobDetailActivity extends AppCompatActivity {

    TextView title;
    TextView employer;
    TextView category;
    TextView description;
    Button applyButton, backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        initUI();
        initListeners();
    }

    private void initListeners() {
        applyButton.setOnClickListener(v ->
                Toast.makeText(this, "Application submitted!", Toast.LENGTH_SHORT).show()
        );
    }

    private void initUI() {
        title = findViewById(R.id.jobTitle);
        employer = findViewById(R.id.jobEmployer);
        category = findViewById(R.id.jobCategory);
        description = findViewById(R.id.jobDesc);
        applyButton = findViewById(R.id.applyButton);
        backButton = findViewById(R.id.backToJobSearchButton);

        title.setText(getIntent().getStringExtra("title"));
        employer.setText(getIntent().getStringExtra("employer"));
        category.setText(getIntent().getStringExtra("category"));
        description.setText(getIntent().getStringExtra("description"));
    }
}
