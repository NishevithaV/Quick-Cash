package com.example.quick_cash.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quick_cash.Models.Job;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class JobsCRUD {
    private final DatabaseReference jobListRef;

    public JobsCRUD(FirebaseDatabase database) {
        this.jobListRef = database.getReference("job_listings");
    }

    private void initializeRefListeners() {
        this.setJobListListener();
    }

    private void setJobListListener(){
    }

    public interface JobsCallback {
        void onCallback(ArrayList<Job> jobs);
    }

    public void getJobs(JobsCallback callback) {
        ArrayList<Job> jobs = new ArrayList<>();

        jobListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot jobSnap : snapshot.getChildren()) {
                    Job job = new Job(
                            String.valueOf(jobSnap.child("title").getValue(String.class)),
                            String.valueOf(jobSnap.child("category").getValue(String.class)),
                            String.valueOf(jobSnap.child("deadline").getValue(String.class)),
                            String.valueOf(jobSnap.child("desc").getValue(String.class)),
                            String.valueOf(jobSnap.child("userID").getValue(String.class))
                    );
                    jobs.add(job);
                }
                callback.onCallback(jobs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

}
