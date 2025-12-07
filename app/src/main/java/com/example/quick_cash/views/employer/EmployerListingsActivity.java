package com.example.quick_cash.views.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;
import com.example.quick_cash.models.Job;
import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.example.quick_cash.utils.UserIdMapper;
import com.example.quick_cash.views.employee.JobDetailActivity;
import com.example.quick_cash.views.employee.JobSearchActivity;
import com.example.quick_cash.views.settings.SettingsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Employer listings activity.
 */
public class EmployerListingsActivity extends AppCompatActivity {

    private Button settingsPageButton;
    private Button postJobButton;
    private ListView jobsListView;
    private Jobs jobsCRUD;
    private ArrayAdapter<String> adapter;
    private List<String> jobTitles = new ArrayList<>();
    private List<Job> displayedJobs = new ArrayList<>();

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
        setContentView(R.layout.activity_employer_listings);
        String userID = getIntent().getStringExtra("userID");
        jobsCRUD = new Jobs(FirebaseDatabase.getInstance());
        initUI();
        initListeners();

        adapter = new ArrayAdapter<>(this, R.layout.job_postings_item, jobTitles);
        jobsListView.setAdapter(adapter);

        if (!getIntent().getBooleanExtra("isTest", false)) loadJobsForUser(userID);
    }

    private void initListeners() {
        postJobButton.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerListingsActivity.this, PostFormActivity.class);
            startActivity(intent);
        });
        settingsPageButton.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerListingsActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
        jobsListView.setOnItemClickListener((parent, view, position, id) -> {
            Job selectedJob = displayedJobs.get(position);

            Intent intent = new Intent(EmployerListingsActivity.this, JobApplicationsActivity.class);
            intent.putExtra("jobID", selectedJob.getId());
            intent.putExtra("jobTitle", selectedJob.getTitle());
            startActivity(intent);
        });
    }

    private void initUI() {
        postJobButton = findViewById(R.id.postJobEmployerButton);
        settingsPageButton = findViewById(R.id.settingsPageEmployerButton);
        jobsListView = findViewById(R.id.jobListView);
    }

    /**
     * Load jobs for user.
     *
     * @param userID the user id
     */
    public void loadJobsForUser(String userID) {
        jobsCRUD.getJobsForUser(userID, new Jobs.JobsCallback() {
            @Override
            public void onCallback(ArrayList<Job> jobs) {
                jobTitles.clear();
                displayedJobs.clear();

                for (Job j : jobs) {
                    String title = j.getTitle();
                    if (title != null && !title.isEmpty()) {
                        jobTitles.add(title);
                        displayedJobs.add(j);
                    }
                }

                if (jobTitles.isEmpty()) {
                    jobTitles.add("No jobs posted yet.");
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCallback(Job job) {
                // Do nothing
            }
        });
    }

    /**
     * Sets displayed jobs for test.
     *
     * @param jobs the jobs
     */
    public void setDisplayedJobsForTest(ArrayList<Job> jobs) {
        displayedJobs.clear();
        displayedJobs.addAll(jobs);
        jobTitles.clear();
        for (Job j : jobs) {
            String title = j.getTitle();
            if (title != null && !title.isEmpty()) {
                jobTitles.add(title);
            }
        }
        if (jobTitles.isEmpty()) {
            jobTitles.add("No jobs posted yet.");
        }
        adapter.notifyDataSetChanged();
    }
}
