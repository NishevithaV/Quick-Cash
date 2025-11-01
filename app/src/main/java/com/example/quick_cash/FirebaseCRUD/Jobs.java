package com.example.quick_cash.FirebaseCRUD;

import android.util.Log;

import com.example.quick_cash.Models.Job;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Jobs {
    private final DatabaseReference jobListRef;

    public Jobs(FirebaseDatabase database) {
        this.jobListRef = database.getReference("job_listings");
    }

    private void initializeRefListeners() {
        this.setJobListListener();
    }

    private void setJobListListener(){
    }

    public boolean postJob(Job job){

        String jobId = jobListRef.push().getKey();
        if (jobId == null) {
            Log.e("Firebase", "Failed to generate jobId");
            return false;
        }

        jobListRef.child(jobId).setValue(job)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Job posted successfully!");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to post job", e);
                });

        return true;
    }

    public void getJob(){
    }

}
