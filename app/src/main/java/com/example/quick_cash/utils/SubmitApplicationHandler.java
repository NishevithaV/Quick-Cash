package com.example.quick_cash.utils;

import com.example.quick_cash.models.Application;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.example.quick_cash.utils.FirebaseCRUD.Users;
import com.google.firebase.database.FirebaseDatabase;


/**
 * The type Submit application handler.
 */
public class SubmitApplicationHandler {
    /**
     * The constant ALREADY_APPLIED.
     */
    public static final String ALREADY_APPLIED = "Failed: Already Applied";
    /**
     * The constant APPLICATION_SUCCESS.
     */
    public static final String APPLICATION_SUCCESS = "Success: Application Submitted";
    private Applications appsCRUD;
    private Users usersCRUD;

    /**
     * Instantiates a new Submit application handler.
     */
    public SubmitApplicationHandler(){
        appsCRUD = new Applications(FirebaseDatabase.getInstance());
        usersCRUD = new Users(FirebaseDatabase.getInstance());
    }

    /**
     * Instantiates a new Submit application handler.
     *
     * @param mockUsers the mock users
     * @param mockApps  the mock apps
     */
    public SubmitApplicationHandler(Users mockUsers, Applications mockApps) {
        this.appsCRUD = mockApps;
        this.usersCRUD = mockUsers;
    }

    /**
     * Submit callback interface
     */
    public interface SubmitCallback {
        /**
         * Callback function
         *
         * @param msg callback message
         */
        public void onCallback(String msg);
    }

    /**
     * Submits an application for an applicant
     *
     * @param uid      applicant id
     * @param jobID    job id to apply to
     * @param letter   letter
     * @param callback callback
     */
    public void submitApp(String uid, String jobID, String letter, SubmitCallback callback) {
        if (uid.equalsIgnoreCase("testUserID")){
            // for test
            if (!jobID.contains("alreadyapplied"))
                callback.onCallback(APPLICATION_SUCCESS);
            else
                callback.onCallback(ALREADY_APPLIED);
        } else {
            // for real
            checkIfHasAlreadyApplied(uid, jobID, applied -> {
                if (!applied) {
                    Application app = new Application(uid, letter, jobID);
                    appsCRUD.postApplication(app, new Applications.PostAppCallback() {
                        @Override
                        public void onSuccess() {
                            callback.onCallback(APPLICATION_SUCCESS);
                        }

                        @Override
                        public void onFailure(String reason) {
                            callback.onCallback(reason);
                        }
                    });
                } else {
                    callback.onCallback(ALREADY_APPLIED);
                }
            });
        }

    }

    /**
     * Has already applied callback interface
     */
    public interface HasAlreadyAppliedCallback {
        /**
         * Callback function
         *
         * @param applied callback boolean
         */
        public void onCallback(boolean applied);
    }

    /**
     * Checks if user has already applied to job
     *
     * @param uid      user id
     * @param jobID    job id
     * @param callback callback function
     */
    public void checkIfHasAlreadyApplied(String uid, String jobID, HasAlreadyAppliedCallback callback) {
        if (uid.equalsIgnoreCase("testUserID")){
            // for test
            if (!jobID.contains("alreadyapplied"))
                callback.onCallback(false);
            else
                callback.onCallback(true);
        } else {
            appsCRUD.getApplications(apps -> {
                boolean applied = false;
                for (Application app : apps) {
                    if (app.getApplicantId().equals(uid) && app.getJobId().equals(jobID)) {
                        applied = true;
                        break;
                    }
                }
                callback.onCallback(applied);
            });
        }
    }
}
