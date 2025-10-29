package com.example.quick_cash.job_posting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.quick_cash.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployerDashboardActivity extends AppCompatActivity {

    private ListView jobsListView;
    private ArrayAdapter<String> adapter;
    private List<String> jobTitles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_dashboard);
        String userID = getIntent().getStringExtra("userID");

        jobsListView = findViewById(R.id.jobListView);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobTitles);
        jobsListView.setAdapter(adapter);

        loadJobsForUser(userID);
    }

    public void loadJobsForUser(String userID) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance()
                .getReference("jobs")
                .child(userID);

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                jobTitles.clear();
                for (DataSnapshot jobSnap : snapshot.getChildren()) {
                    String title = jobSnap.child("title").getValue(String.class);
                    if (title != null) {
                        jobTitles.add(title);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(EmployerDashboardActivity.this,
                        "Failed to load jobs.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
