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

                        adapter.notifyDataSetChanged();

                        if (jobTitles.isEmpty()) {
                            Toast.makeText(EmployerDashboardActivity.this,
                                    "No jobs posted yet.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(EmployerDashboardActivity.this,
                                "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
