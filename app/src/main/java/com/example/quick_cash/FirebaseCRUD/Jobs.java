package com.example.quick_cash.FirebaseCRUD;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class Jobs {
    private final FirebaseDatabase database;
    private DatabaseReference jobListRef;

    public Jobs(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.initializeRefListeners();
    }

    private void initializeDatabaseRefs() {
        this.jobListRef = this.database.getReference("job_listings");
    }

    private void initializeRefListeners() {
        this.setJobListListener();
    }

    private void setJobListListener(){
    }

    public boolean postJob(Map<String, String> job){

        String jobId = jobListRef.push().getKey();
        if (jobId == null){
            System.err.println("Could not generate Job ID");
            return false;
        };
        jobListRef.child(jobId).setValue(job);
        return true;
    }

    public void getJob(){
    }

}
