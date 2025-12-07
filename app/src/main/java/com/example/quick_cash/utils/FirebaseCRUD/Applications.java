package com.example.quick_cash.utils.FirebaseCRUD;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quick_cash.models.Application;
import com.example.quick_cash.utils.JobIdMapper;
import com.example.quick_cash.utils.UserIdMapper;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * The type Applications.
 */
public class Applications {
    private final DatabaseReference appsRef;

    /**
     * Instantiates a new Jobs.
     *
     * @param database the database
     */
    public Applications(FirebaseDatabase database) {
        this.appsRef = database.getReference("job_applications");
    }

    /**
     * The interface Post app callback.
     */
    public interface PostAppCallback {
        /**
         * On success.
         */
        public void onSuccess();

        /**
         * On failure.
         *
         * @param reason the reason
         */
        public void onFailure(String reason);
    }

    /**
     * Post Application boolean.
     *
     * @param app      the application
     * @param callback the callback
     */
    public void postApplication(Application app, PostAppCallback callback){

        String appId = appsRef.push().getKey();
        if (appId == null) {
            Log.e("Firebase", "Failed to generate ApplicationId");
            callback.onFailure("Failed to generate ApplicationId");
        } else {
            appsRef.child(appId).setValue(app)
                    .addOnSuccessListener(aVoid -> {
                        Log.d("Firebase", "Application posted successfully!");
                        callback.onSuccess();
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firebase", "Failed to post Application", e);
                        callback.onFailure("Failed to post Application");
                    });
        }
    }

    /**
     * The interface Application callback.
     */
    public interface AppsCallback {
        /**
         * On callback.
         *
         * @param apps the applications
         */
        void onCallback(ArrayList<Application> apps);
    }

    /**
     * Gets Applications.
     *
     * @param callback the callback
     */
    public void getApplications(AppsCallback callback) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid;
        boolean shouldFilter;
        if (user != null) {
            uid = user.getUid();
            shouldFilter = true;
        } else {
            uid = "";
            shouldFilter = false;
        }

        ArrayList<Application> apps = new ArrayList<>();

        appsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int totalApps = (int) snapshot.getChildrenCount();
                final int[] processedCount = {0};
                for (DataSnapshot appSnap : snapshot.getChildren()) {
                    String jobId = String.valueOf(appSnap.child("jobId").getValue(String.class));
                    Application app = new Application(
                            String.valueOf(appSnap.child("applicantId").getValue(String.class)),
                            String.valueOf(appSnap.child("letter").getValue(String.class)),
                            String.valueOf(appSnap.child("status").getValue(String.class)),
                            jobId,
                            appSnap.getKey()
                    );
                    if (shouldFilter) {
                        UserIdMapper.getRole(uid, role -> {
                            if (role.equalsIgnoreCase("Employer")){
                                JobIdMapper.getEmployer(jobId, employerId -> {
                                    if (uid.equals(employerId)){
                                        apps.add(app);
                                    }
                                    processedCount[0]++;

                                    if (processedCount[0] == totalApps) {
                                        callback.onCallback(apps);
                                    }
                                });
                            } else {
                                if (app.getApplicantId().equals(uid)){
                                    apps.add(app);
                                }
                                processedCount[0]++;

                                if (processedCount[0] == totalApps) {
                                    callback.onCallback(apps);
                                }
                            }
                        });

                    } else {
                        apps.add(app);
                        processedCount[0]++;
                        if (processedCount[0] == totalApps) {
                            callback.onCallback(apps);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });
    }

    /**
     * Update application status.
     *
     * @param appId     the application id
     * @param newStatus the new status
     */
    public void updateStatus(String appId, String newStatus) {
        appsRef.child(appId).child("status").setValue(newStatus);
    }

}
