package com.example.quick_cash.job_posting;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JobPostingActivity extends AppCompatActivity {

    EditText jobName;

    Spinner jobCategorySpinner;
    EditText applicationDeadline;
    EditText jobDescription;
    MaterialButton postButton;
    TextView result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_post_form);

        initUIElements();
        loadJobCategorySpinner();
//        initListeners();
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
        typeAdapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        jobCategorySpinner.setAdapter(typeAdapter);
    }

    protected void setupPostJobButton() {
    }

    protected void initUIElements() {
        jobName = findViewById(R.id.JobTitleField);
        jobCategorySpinner = findViewById(R.id.JobCategorySpinner);
        applicationDeadline = findViewById(R.id.ApplicationDeadlineField);
        jobDescription = findViewById(R.id.JobDescriptionField);
        result = findViewById(R.id.Result);
    }


//    protected void initListeners() {
//        postButton = findViewById(R.id.PostJobButton);
//
//        postButton.setOnClickListener( v-> {
//            String enteredJobName = jobName.getText().toString();
//            String enteredApplicationDeadline = applicationDeadline.getText().toString();
//            String enteredJobDescription = jobDescription.getText().toString();
//
//            Map<String, String> jobObject = new HashMap<>();
//            jobObject.put("name", enteredJobName);
//            jobObject.put("deadline", enteredApplicationDeadline);
//            jobObject.put("desc", enteredJobDescription);

//            String jobId = dbRef.push().getKey();
//
//            boolean postSuccessful = false;
//
//            assert jobId != null;
//            dbRef.child(jobId).setValue(jobObject);
//            postSuccessful = true;
//
//            if (postSuccessful){
//                showSuccessMessage();
//                return;
//            }
//            result.setText("Job posting failed");
//        });
//
//
//    }
//
//    private void showSuccessMessage(){
//        result.setText("Job posted successfully");
//    }
}