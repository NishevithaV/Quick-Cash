package com.example.quick_cash.employer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quick_cash.R;
import com.example.quick_cash.settings.SettingsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployerDashboardActivity extends AppCompatActivity {

    private Button settingsPageButton;
    private Button postJobButton;
    private ListView jobsListView;
    private ArrayAdapter<String> adapter;
    private List<String> jobTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_dashboard);
        String userID = getIntent().getStringExtra("userID");

        initUI();
        initListeners();

        // Check if we should show the success message
        if (getIntent().getBooleanExtra("show_success_message", false)) {
            Toast.makeText(this, "Role switched successfully", Toast.LENGTH_SHORT).show();
        }

        adapter = new ArrayAdapter<>(this, R.layout.job_postings_item, jobTitles);
        jobsListView.setAdapter(adapter);

        loadJobsForUser(userID);
    }

    private void initListeners() {
        postJobButton.setOnClickListener(v -> {
            Intent intent = new Intent(com.example.quick_cash.employer.EmployerDashboardActivity.this, PostFormActivity.class);
            startActivity(intent);
        });
        settingsPageButton.setOnClickListener(v -> {
            Intent intent = new Intent(com.example.quick_cash.employer.EmployerDashboardActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }

    private void initUI() {
        postJobButton = findViewById(R.id.postJobEmployerButton);
        settingsPageButton = findViewById(R.id.settingsPageEmployerButton);
        jobsListView = findViewById(R.id.jobListView);
    }

    public void loadJobsForUser(String userID) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("job_listings");

        dbRef.orderByChild("userID").equalTo(userID)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        jobTitles.clear();

                        for (DataSnapshot jobSnap : snapshot.getChildren()) {
                            String title = jobSnap.child("title").getValue(String.class);
                            if (title != null && !title.isEmpty()) jobTitles.add(title);
                        }

                        if (jobTitles.isEmpty()) {
                            jobTitles.add("No jobs posted yet.");
                        }

                        adapter.notifyDataSetChanged();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EmployerDashboardActivity.this,
                                "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
