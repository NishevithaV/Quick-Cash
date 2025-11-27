package com.example.quick_cash.utils.FirebaseCRUD;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quick_cash.models.Application;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

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
     * Post Application boolean.
     *
     * @param app the application
     * @return the boolean posted or not
     */
    public boolean postApplication(Application app){

        String appId = appsRef.push().getKey();
        if (appId == null) {
            Log.e("Firebase", "Failed to generate ApplicationId");
            return false;
        }

        appsRef.child(appId).setValue(app)
                .addOnSuccessListener(aVoid -> {
                    Log.d("Firebase", "Application posted successfully!");
                })
                .addOnFailureListener(e -> {
                    Log.e("Firebase", "Failed to post Application", e);
                });
        return true;
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
        ArrayList<Application> apps = new ArrayList<>();

        appsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot appSnap : snapshot.getChildren()) {
                    Application app = new Application(
                            String.valueOf(appSnap.child("applicantId").getValue(String.class)),
                            String.valueOf(appSnap.child("coverLtr").getValue(String.class)),
                            String.valueOf(appSnap.child("status").getValue(String.class)),
                            String.valueOf(appSnap.child("jobId").getValue(String.class)),
                            appSnap.getKey()
                    );
                    apps.add(app);
                }
                callback.onCallback(apps);
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
     * @param appId   the application id
     * @param newStatus  the new status
     */
    public void updateStatus(String appId, String newStatus) {
        appsRef.child(appId).child("status").setValue(newStatus);
    }

}
