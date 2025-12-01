package com.example.quick_cash.views.employee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.example.quick_cash.models.Job;
import com.example.quick_cash.R;
import com.example.quick_cash.utils.JobAdapter;
import com.example.quick_cash.utils.JobSearchHandler;
import com.example.quick_cash.utils.LocationHandler;
import com.example.quick_cash.utils.UserIdMapper;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;

public class JobSearchActivity extends AppCompatActivity {

    EditText userSearch;
    TextView resultsHeader;
    TextView currentLocationHeader;
    ListView resultsView;
    Button searchBtn;
    Spinner categorySelector;
    Spinner locationSelector;

    Jobs jobsCRUD;

    JobSearchHandler jobSearcher;

    private ArrayList<Job> displayedJobs;

    LocationHandler locationHandler;
    private static final int REQUEST_LOCATION = 1;

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
        displayedJobs = new ArrayList<>();

        locationHandler = LocationHandler.getInstance(this);
        locationHandler.setCallback(location ->
                currentLocationHeader.setText(location)
        );
        requestLocationPermission();

        jobsCRUD = new Jobs(FirebaseDatabase.getInstance());
        jobsCRUD.getJobs(new Jobs.JobsCallback() {
            @Override
            public void onCallback(ArrayList<Job> callbackJobs) {
                jobSearcher = new JobSearchHandler(callbackJobs);
                displayJobs(new ArrayList<>(callbackJobs));
                initListeners();
            }

            @Override
            public void onCallback(Job job) {
                // Do nothing
            }
        });

    }

    private void initUI() {
        this.userSearch = findViewById(R.id.userSearch);
        this.searchBtn = findViewById(R.id.searchBtn);
        this.resultsHeader = findViewById(R.id.textViewResHead);
        this.resultsView = findViewById(R.id.resultsView);
        this.currentLocationHeader = findViewById(R.id.currentLocationHeader);

        this.categorySelector = findViewById(R.id.catSelect);
        ArrayList<String> categories = new ArrayList<String>(
                Arrays.asList("Category", "Finance", "Tech", "Education", "Health", "Construction", "AI")
        );
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(
                this,
                R.layout.category_item,
                categories
        );
        categoryAdapter.setDropDownViewResource(R.layout.category_item);
        categorySelector.setAdapter(categoryAdapter);

        this.locationSelector = findViewById(R.id.locationSelect);
        ArrayList<String> locations = new ArrayList<String>(
                Arrays.asList("All Jobs", "Nearby Jobs")
        );
        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                this,
                R.layout.category_item,
                locations
        );
        locationAdapter.setDropDownViewResource(R.layout.category_item);
        locationSelector.setAdapter(locationAdapter);
    }

    private void initListeners() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJobs(userSearch.getText().toString().trim(),
                        categorySelector.getSelectedItem().toString(),
                        locationSelector.getSelectedItem().toString());
            }
        });

        resultsView.setOnItemClickListener((parent, view, position, id) -> {
            Job selectedJob = displayedJobs.get(position);

            Intent intent = new Intent(JobSearchActivity.this, JobDetailActivity.class);
            intent.putExtra("title", selectedJob.getTitle());
            intent.putExtra("category", selectedJob.getCategory());
            intent.putExtra("location", selectedJob.getLocation());
            intent.putExtra("description", selectedJob.getDesc());
            intent.putExtra("jobID", selectedJob.getId());
            UserIdMapper.getName(selectedJob.getUserID(), name -> {
                intent.putExtra("employer", name);
                startActivity(intent);
            });
        });
    }

    private void requestLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION
            );

        } else {
            startLocationUpdates();
        }
    }

    private void startLocationUpdates() {
        locationHandler.startUpdates();
        currentLocationHeader.setText(locationHandler.getDetectedLocation());
    }

    private void loadJobs(String search, String category, String location) {
        ArrayList<Job> jobsToLoad
                = jobSearcher.getAllJobs(search, category, location, locationHandler.getDetectedLocation());

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


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == REQUEST_LOCATION &&
                grantResults.length > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            startLocationUpdates();
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // todo: remove this later
    private String getLocation() {return "test";}

}
