package com.example.quick_cash.views.employee;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobSearchActivity extends AppCompatActivity {

    EditText userSearch;
    EditText locationSearchField;
    TextView resultsHeader;
    TextView currentLocationHeader;
    ListView resultsView;
    Button searchBtn;
    Spinner categorySelector;
    Spinner locationSelector;
    Spinner radiusSelector;

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
        jobsCRUD.getJobs(callbackJobs -> {
            jobSearcher = new JobSearchHandler(callbackJobs);
            displayJobs(new ArrayList<>(callbackJobs));
            initListeners();
        });

    }


    private void initUI() {
        this.userSearch = findViewById(R.id.userSearch);
        this.locationSearchField = findViewById(R.id.locationSearchField);
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

        this.radiusSelector = findViewById(R.id.radiusSelect);
        ArrayList<String> radiusOptions = new ArrayList<String>(
                Arrays.asList("Radius (km)", "5 km", "10 km", "25 km", "50 km", "100 km")
        );
        ArrayAdapter<String> radiusAdapter = new ArrayAdapter<>(
                this,
                R.layout.category_item,
                radiusOptions
        );
        radiusAdapter.setDropDownViewResource(R.layout.category_item);
        radiusSelector.setAdapter(radiusAdapter);
    }

    private void initListeners() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = userSearch.getText().toString().trim();
                String category = categorySelector.getSelectedItem().toString();
                String locationFilter = locationSelector.getSelectedItem().toString();
                String locationSearch = locationSearchField.getText().toString().trim();
                String radiusStr = radiusSelector.getSelectedItem().toString();

                // Handle location-based search with radius
                if (!locationSearch.isEmpty() && !radiusStr.equals("Radius (km)")) {
                    loadJobsByLocationRadius(searchText, category, locationSearch, radiusStr);
                } else {
                    // Default search behavior
                    loadJobs(searchText, category, locationFilter);
                }
            }
        });

        resultsView.setOnItemClickListener((parent, view, position, id) -> {
            Job selectedJob = displayedJobs.get(position);

            Intent intent = new Intent(JobSearchActivity.this, JobDetailActivity.class);
            intent.putExtra("title", selectedJob.getTitle());
            intent.putExtra("category", selectedJob.getCategory());
            intent.putExtra("location", selectedJob.getLocation());
            intent.putExtra("description", selectedJob.getDesc());
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

    private void loadJobsByLocationRadius(String search, String category, String locationSearch, String radiusStr) {
        // Parse radius
        int radius;
        try {
            radius = Integer.parseInt(radiusStr.split(" ")[0]);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please select a valid radius", Toast.LENGTH_SHORT).show();
            return;
        }

        // Geocode the search location
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(locationSearch, 1);
            if (addresses == null || addresses.isEmpty()) {
                Toast.makeText(this, "Unable to find location. Please try a different address.", Toast.LENGTH_SHORT).show();
                return;
            }

            Address address = addresses.get(0);
            double searchLat = address.getLatitude();
            double searchLng = address.getLongitude();

            // Filter jobs by radius
            ArrayList<Job> jobsToLoad = jobSearcher.getJobsByLocationRadius(
                    search, category, searchLat, searchLng, radius
            );

            if (jobsToLoad.isEmpty()) {
                resultsHeader.setText("No jobs found in this location");
            }
            displayJobs(jobsToLoad);

        } catch (IOException e) {
            Toast.makeText(this, "Unable to find location. Please try a different address.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
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
