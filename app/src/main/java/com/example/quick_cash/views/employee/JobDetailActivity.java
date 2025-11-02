package com.example.quick_cash.views.employee;

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
    Button applyButton;

    /**
     * Overriden onCreate function to start activity, initialize UI, properties, and set listeners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
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

        title.setText(getIntent().getStringExtra("title"));
        employer.setText(getIntent().getStringExtra("employer"));
        category.setText(getIntent().getStringExtra("category"));
        description.setText(getIntent().getStringExtra("description"));
    }
}
