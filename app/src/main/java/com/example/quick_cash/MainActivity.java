package com.example.quick_cash;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference dbRef;

    EditText jobName;
    EditText applicationDeadline;
    EditText jobDescription;
    MaterialButton postButton;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_main);
        initUIElements();
        initListeners();
        initDatabase();
    }

    private void initDatabase(){
        database = FirebaseDatabase.getInstance();
        dbRef = database.getReference("job_listings");
    }

    private void initUIElements() {
        jobName = findViewById(R.id.JobNameField);
        applicationDeadline = findViewById(R.id.ApplicationDeadlineField);
        jobDescription = findViewById(R.id.JobDescriptionField);
        result = findViewById(R.id.result);
    }


    private void initListeners() {
        postButton = findViewById(R.id.PostJobButton);

        postButton.setOnClickListener( v-> {
            String enteredJobName = jobName.getText().toString();
            String enteredApplicationDeadline = applicationDeadline.getText().toString();
            String enteredJobDescription = jobDescription.getText().toString();

            Map<String, String> jobObject = new HashMap<>();
            jobObject.put("name", enteredJobName);
            jobObject.put("deadline", enteredApplicationDeadline);
            jobObject.put("desc", enteredJobDescription);

            String jobId = dbRef.push().getKey();

            boolean postSuccessful = false;

            assert jobId != null;
            dbRef.child(jobId).setValue(jobObject);
            postSuccessful = true;

            if (postSuccessful){
                showSuccessMessage();
                return;
            }
            result.setText("Job posted successfully");
        });


    }

    private void showSuccessMessage(){
        result.setText("Job posted successfully");
    }
}