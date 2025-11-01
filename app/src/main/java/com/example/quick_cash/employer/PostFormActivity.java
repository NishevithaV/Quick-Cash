package com.example.quick_cash.employer;

import android.content.Intent;
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

import com.example.quick_cash.FirebaseCRUD.Jobs;
import com.example.quick_cash.Models.Job;
import com.example.quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PostFormActivity extends AppCompatActivity implements View.OnClickListener {

    Jobs jobsCRUD;
    JobPostingValidator validator;
    EditText jobName;

    Spinner jobCategorySpinner;
    EditText applicationDeadline;
    EditText jobDescription;
    TextView result;

    private FirebaseAuth auth;


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

    protected FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://quickcash-72ee9-default-rtdb.firebaseio.com/");
    }

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

    protected void setupPostJobButton() {
        Button postJobButton = findViewById(R.id.PostJobButton);
        postJobButton.setOnClickListener(this);
    }

    protected void initUIElements() {
        jobName = findViewById(R.id.JobTitleField);
        jobCategorySpinner = findViewById(R.id.JobCategorySpinner);
        applicationDeadline = findViewById(R.id.ApplicationDeadlineField);
        jobDescription = findViewById(R.id.JobDescriptionField);
        result = findViewById(R.id.Result);
    }

    private void showToast(String message) {
        Toast.makeText(PostFormActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        String userId = getCurrentUserID();
        String enteredJobTitle = jobName.getText().toString().trim();
        String enteredJobCategory = jobCategorySpinner.getSelectedItem().toString();
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

        Job job = new Job(
                enteredJobTitle,
                enteredJobCategory,
                enteredJobDeadline,
                enteredJobDescription,
                userId);

        boolean postedSuccessfully = jobsCRUD.postJob(job);

        if (!postedSuccessfully) {
            showToast("Job posting failed");
            result.setText(getResources().getString(R.string.EMPTY_STRING));
            return;
        };

        showToast("Job posted successfully");
        result.setText(getResources().getString(R.string.EMPTY_STRING));
        move2EmployerDashboard(job);
    }

    public String getCurrentUserID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return "TEST_UID";
        }
        return user.getUid();
    }

    protected void move2EmployerDashboard(Job job) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, EmployerDashboardActivity.class);
            intent.putExtra("userID", job.getUserID());
            startActivity(intent);
            finish();
        }, 1000);
    }
}