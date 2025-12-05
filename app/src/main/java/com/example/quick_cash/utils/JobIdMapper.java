package com.example.quick_cash.utils;


import com.example.quick_cash.models.Job;
import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public final class JobIdMapper {

    private static final Jobs jobsCRUD = new Jobs(FirebaseDatabase.getInstance());

    private JobIdMapper() {} // Prevent instantiation

    /**
     * Get Job Title given user id
     *
     * @param jobId   id to get name for
     * @param callback interface with method to handle user name
     */
    public static void getTitle(String jobId, JobInfoCallback callback) {
        jobsCRUD.getJob(jobId, new Jobs.JobsCallback() {
            @Override
            public void onCallback(Job job){
                String title = job.getTitle();
                callback.onInfoLoaded(title != null && !title.equals("null") ? title : "Test Job Title");
            }

            @Override
            public void onCallback(ArrayList<Job> job){
                // Do nothing
            }
        });
    }

    /**
     * Interface to be used as callback
     */
    public interface JobInfoCallback {
        /**
         * Run On info loaded. To be implemented
         *
         * @param info the info
         */
        void onInfoLoaded(String info);
    }

    /**
     * Get Job Title given user id
     *
     * @param jobId   id to get name for
     * @param callback interface with method to handle user name
     */
    public static void getEmployer(String jobId, JobInfoCallback callback) {
        jobsCRUD.getJob(jobId, new Jobs.JobsCallback() {
            @Override
            public void onCallback(Job job){
                String userID = job.getUserID();
                callback.onInfoLoaded(userID != null && !userID.equals("null") ? userID : "Test Employer ID");
            }

            @Override
            public void onCallback(ArrayList<Job> job){
                // Do nothing
            }
        });
    }
}
