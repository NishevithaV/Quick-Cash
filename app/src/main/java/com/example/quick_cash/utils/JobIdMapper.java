package com.example.quick_cash.utils;


import com.example.quick_cash.models.Job;
import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public final class JobIdMapper {

    private static final Jobs jobsCRUD = new Jobs(FirebaseDatabase.getInstance());

    private JobIdMapper() {} // Prevent instantiation

    /**
     * Get user name given user id
     *
     * @param jobId   id to get name for
     * @param callback interface with method to handle user name
     */
    public static void getTitle(String jobId, JobTitleCallback callback) {
        jobsCRUD.getJob(jobId, new Jobs.JobsCallback() {
            @Override
            public void onCallback(Job job){
                String title = job.getTitle();
                callback.onTitleLoaded(title != null && !title.equals("null") ? title : "Test Job Title");
            }

            @Override
            public void onCallback(ArrayList<Job> job){
                // Do nothing
            }
        });
    }

    /**
     * Interface to be used as callback with getName()
     */
    public interface JobTitleCallback {
        /**
         * Run On title loaded. To be implemented
         *
         * @param title the title
         */
        void onTitleLoaded(String title);
    }
}
