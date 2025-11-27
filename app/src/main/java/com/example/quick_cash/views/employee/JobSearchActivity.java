package com.example.quick_cash.views.employee;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.example.quick_cash.models.Job;
import com.example.quick_cash.R;
import com.example.quick_cash.utils.JobAdapter;
import com.example.quick_cash.utils.JobSearchHandler;
import com.example.quick_cash.utils.UserIdMapper;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class JobSearchActivity extends AppCompatActivity {

    EditText userSearch;
    TextView resultsHeader;
    ListView resultsView;
    Button searchBtn;
    Spinner categorySelector;

    Jobs jobsCRUD;

    JobSearchHandler jobSearcher;

    private ArrayList<Job> displayedJobs;

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
        this.setContentView(R.layout.activity_job_list);
        initUI();
        jobsCRUD = new Jobs(FirebaseDatabase.getInstance());
        jobsCRUD.getJobs(new Jobs.JobsCallback() {
            @Override
            public void onCallback(ArrayList<Job> jobs) {
                jobSearcher = new JobSearchHandler(jobs);
                loadJobs("", "");
                initListeners();
            }

            @Override
            public void onCallback(Job job) {
                // Do nothing
            }
        });
        displayedJobs = new ArrayList<>();
    }

    private void initUI() {
        this.userSearch = findViewById(R.id.userSearch);
        this.searchBtn = findViewById(R.id.searchBtn);
        this.resultsHeader = findViewById(R.id.textViewResHead);
        this.resultsView = findViewById(R.id.resultsView);
        this.categorySelector = findViewById(R.id.catSelect);
        ArrayList<String> categories = new ArrayList<String>(
                Arrays.asList("Category", "Finance", "Tech", "Education", "Health", "Construction", "AI")
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.category_item, // use your custom layout
                categories
        );
        adapter.setDropDownViewResource(R.layout.category_item);
        categorySelector.setAdapter(adapter);
    }

    private void initListeners() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJobs(userSearch.getText().toString().trim(),
                        categorySelector.getSelectedItem().toString());
            }
        });

        resultsView.setOnItemClickListener((parent, view, position, id) -> {
            Job selectedJob = displayedJobs.get(position);

            Intent intent = new Intent(JobSearchActivity.this, JobDetailActivity.class);
            intent.putExtra("title", selectedJob.getTitle());
            intent.putExtra("category", selectedJob.getCategory());
            intent.putExtra("description", selectedJob.getDesc());
            UserIdMapper.getName(selectedJob.getUserID(), name -> {
                intent.putExtra("employer", name);
                startActivity(intent);
            });
        });
    }

    private void loadJobs(String search, String category) {
        ArrayList<Job> jobsToLoad = jobSearcher.getAllJobs(search, category);
        displayJobs(jobsToLoad);
    }

    private void displayJobs(ArrayList<Job> jobs) {
        displayedJobs.clear();
        displayedJobs.addAll(jobs);

        if (displayedJobs.isEmpty()) {
            resultsView.setVisibility(View.GONE);
            resultsHeader.setText(R.string.NO_RESULT);
        } else {
            resultsHeader.setText(R.string.RESULT);
            resultsView.setVisibility(View.VISIBLE);

            JobAdapter adapter = new JobAdapter(this, R.layout.search_results_item, new ArrayList<>(jobs));
            resultsView.setAdapter(adapter);


            adapter.notifyDataSetChanged();
        }
    }
}
