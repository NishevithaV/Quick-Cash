package com.example.quick_cash.views.employee;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.SubmitApplicationHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class JobDetailActivity extends AppCompatActivity {

    TextView title;
    TextView employer;
    TextView category;
    TextView description;
    Button applyButton;
    private SubmitApplicationHandler submitHandler;
    private String jobID;

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
        submitHandler = new SubmitApplicationHandler();
        jobID = getIntent().getStringExtra("jobID");
        initUI();
        initListeners();
    }

    private void initListeners() {
        applyButton.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent = new Intent(JobDetailActivity.this, SubmitApplicationActivity.class);
                   intent.putExtra("jobID", jobID);
                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                   startActivity(intent);
               }
           }
        );
    }

    private void initUI() {
        title = findViewById(R.id.jobTitle);
        employer = findViewById(R.id.jobEmployer);
        category = findViewById(R.id.jobCategory);
        description = findViewById(R.id.jobDesc);
        applyButton = findViewById(R.id.applyButton);

        // Check if user has already applied to job
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user!=null ? user.getUid() : "testUserID";
        submitHandler.checkIfHasAlreadyApplied(uid, jobID, new SubmitApplicationHandler.HasAlreadyAppliedCallback() {
            @Override
            public void onCallback(boolean applied) {
                if (!applied) applyButton.setVisibility(VISIBLE);
                else ((TextView) findViewById(R.id.jobDetailAlreadyApplied)).setText(R.string.ALREADY_APPLIED);
            }
        });

        title.setText(getIntent().getStringExtra("title"));
        employer.setText(getIntent().getStringExtra("employer"));
        category.setText(getIntent().getStringExtra("category"));
        description.setText(getIntent().getStringExtra("description"));
    }
}
