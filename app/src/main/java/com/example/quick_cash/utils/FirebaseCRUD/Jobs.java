package com.example.quick_cash.utils.FirebaseCRUD;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quick_cash.models.Job;
import com.example.quick_cash.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Jobs {
    private final DatabaseReference jobListRef;

    /**
     * Instantiates a new Jobs.
     *
     * @param database the database
     */
    public Jobs(FirebaseDatabase database) {
        this.jobListRef = database.getReference("job_listings");
    }

    /**
     * Post job boolean.
     *
     * @param job the job
     * @return the boolean
     */
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

    /**
     * The interface Jobs callback.
     */
    public interface JobsCallback {
        /**
         * On callback.
         *
         * @param jobs the jobs
         */
        void onCallback(ArrayList<Job> jobs);

        /**
         * On callback.
         *
         * @param job the job
         */
        void onCallback(Job job);
    }

    /**
     * Gets job given job id.
     *
     * @param callback the callback
     */
    public void getJob(String jobId, JobsCallback callback) {
        final Job[] jobArr = new Job[1];

        jobListRef.child(jobId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                jobArr[0] = new Job(
                        String.valueOf(snapshot.child("title").getValue(String.class)),
                        String.valueOf(snapshot.child("category").getValue(String.class)),
                        String.valueOf(snapshot.child("deadline").getValue(String.class)),
                        String.valueOf(snapshot.child("desc").getValue(String.class)),
                        String.valueOf(snapshot.child("userID").getValue(String.class))
                );
                callback.onCallback(jobArr[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });

    }

    /**
     * Gets jobs.
     *
     * @param callback the callback
     */
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
                // Do nothing
            }
        });

    }


}
