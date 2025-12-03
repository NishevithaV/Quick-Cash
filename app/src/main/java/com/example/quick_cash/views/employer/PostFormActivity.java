package com.example.quick_cash.views.employer;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.controllers.JobPostingValidator;
import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.example.quick_cash.models.Job;
import com.example.quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostFormActivity extends AppCompatActivity implements View.OnClickListener {

    Jobs jobsCRUD;
    JobPostingValidator validator;
    EditText jobName;
    EditText jobLocation;

    Spinner jobCategorySpinner;
    EditText applicationDeadline;
    EditText jobDescription;
    TextView result;

    private FirebaseAuth auth;

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
        this.setContentView(R.layout.activity_post_form);
        auth = FirebaseAuth.getInstance();
        this.validator = new JobPostingValidator();
        this.jobsCRUD = new Jobs(getFirebaseDatabase());
        initUIElements();
        this.loadJobCategorySpinner();
        this.setupPostJobButton();
    }

    /**
     * Gets firebase database.
     *
     * @return the firebase database
     */
    protected FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://quickcash-72ee9-default-rtdb.firebaseio.com/");
    }

    /**
     * Load job category spinner.
     */
    protected void loadJobCategorySpinner() {
        jobCategorySpinner = findViewById(R.id.JobCategorySpinner);
        List<String> types = new ArrayList<>();
        types.add("Select job category");
        types.add("IT");
        types.add("Finance");
        types.add("Healthcare");
        types.add("AI");
        types.add("engineering");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobCategorySpinner.setAdapter(typeAdapter);
    }

    /**
     * Sets post job button.
     */
    protected void setupPostJobButton() {
        Button postJobButton = findViewById(R.id.PostJobButton);
        postJobButton.setOnClickListener(this);
    }

    /**
     * Init ui elements.
     */
    protected void initUIElements() {
        jobName = findViewById(R.id.JobTitleField);
        jobLocation = findViewById(R.id.JobLocationField);
        jobCategorySpinner = findViewById(R.id.JobCategorySpinner);
        applicationDeadline = findViewById(R.id.ApplicationDeadlineField);
        jobDescription = findViewById(R.id.JobDescriptionField);
        result = findViewById(R.id.Result);
    }

    private void showToast(String message) {
        Toast.makeText(PostFormActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Validate and move to Employer dashboard when view is clicked
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        String userId = getCurrentUserID();
        String enteredJobTitle = jobName.getText().toString().trim();
        String enteredJobCategory = jobCategorySpinner.getSelectedItem().toString();
        String enteredJobLocation = jobLocation.getText().toString().trim();
        String enteredJobDeadline = applicationDeadline.getText().toString().trim();
        String enteredJobDescription = jobDescription.getText().toString().trim();

        String errorMessage = "";
        if (validator.checkEmptyJobTitle(enteredJobTitle)) {
            errorMessage = getResources().getString(R.string.EMPTY_JOB_TITLE);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (!validator.checkValidJobCategory(enteredJobCategory)) {
            errorMessage = getResources().getString(R.string.INVALID_JOB_CATEGORY);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (validator.checkEmptyJobLocation(enteredJobLocation)) {
            errorMessage = getResources().getString(R.string.EMPTY_LOCATION);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (validator.checkEmptyApplicationDeadline(enteredJobDeadline)) {
            errorMessage = getResources().getString(R.string.EMPTY_DEADLINE);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (!validator.checkValidApplicationDeadline(enteredJobDeadline)) {
            errorMessage = getResources().getString(R.string.INVALID_APPLICATION_DEADLINE);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (validator.checkEmptyJobDescription(enteredJobDescription)) {
            errorMessage = getResources().getString(R.string.EMPTY_DESCRIPTION);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        // Geocode the location to get coordinates
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(enteredJobLocation, 1);
            if (addresses == null || addresses.isEmpty()) {
                errorMessage = getResources().getString(R.string.INVALID_LOCATION);
                result.setText(errorMessage);
                showToast(errorMessage);
                return;
            }

            Address address = addresses.get(0);
            double latitude = address.getLatitude();
            double longitude = address.getLongitude();

            Job job = new Job(
                    enteredJobTitle,
                    enteredJobCategory,
                    enteredJobLocation,
                    enteredJobDeadline,
                    enteredJobDescription,
                    userId,
                    latitude,
                    longitude);

            boolean postedSuccessfully = jobsCRUD.postJob(job);

            if (!postedSuccessfully) {
                showToast("Job posting failed");
                result.setText(getResources().getString(R.string.EMPTY_STRING));
                return;
            }

            showToast("Job posted successfully");
            result.setText(getResources().getString(R.string.EMPTY_STRING));
            move2EmployerDashboard(job);

        } catch (IOException e) {
            errorMessage = getResources().getString(R.string.INVALID_LOCATION);
            result.setText(errorMessage);
            showToast(errorMessage);
            e.printStackTrace();
        }
    }

    /**
     * Gets current user id.
     *
     * @return the current user id
     */
    public String getCurrentUserID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return "TEST_UID";
        }
        return user.getUid();
    }

    /**
     * Move 2 employer dashboard.
     *
     * @param job the job
     */
    protected void move2EmployerDashboard(Job job) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, EmployerDashboardActivity.class);
            intent.putExtra("userID", job.getUserID());
            startActivity(intent);
            finish();
        }, 1000);
    }
}