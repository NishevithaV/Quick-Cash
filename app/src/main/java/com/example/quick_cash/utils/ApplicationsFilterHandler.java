package com.example.quick_cash.utils;


import com.example.quick_cash.models.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Applications filter handler.
 */
public class ApplicationsFilterHandler {
    private ArrayList<Application> allApps;

    /**
     * Instantiates a new Application search handler.
     *
     * @param apps the jobs
     */
    public ApplicationsFilterHandler(List<Application> apps) {
        updateAllApps(apps);
    }

    private void updateAllApps(List<Application> sampleApps) {
        allApps = new ArrayList<>(sampleApps);
    }

    private boolean matchStatus(Application a, String status) {
        return a.getStatus().equalsIgnoreCase(status);
    }

    private boolean matchJob(Application a, String jobID) {
        return a.getJobId().equals(jobID);
    }
    private ArrayList<Application> filterAppsByStatus(String search) {
        ArrayList<Application> results = new ArrayList<>();

        for (Application a : allApps) {
            if (matchStatus(a, search)) {
                results.add(a);
            }
        }

        return results;
    }

    private ArrayList<Application> filterAppsByJob(String search) {
        ArrayList<Application> results = new ArrayList<>();

        for (Application a : allApps) {
            if (matchJob(a, search)) {
                results.add(a);
            }
        }

        return results;
    }

    /**
     * Gets all apps of specified status.
     *
     * @param status the search
     * @return the matching apps
     */
    public ArrayList<Application> getAppsByStatus(String status) {
        return filterAppsByStatus(status);
    }

    /**
     * Gets all apps for specified job.
     *
     * @param jobID the search
     * @return the matching apps
     */
    public ArrayList<Application> getAppsByJob(String jobID) {
        return filterAppsByJob(jobID);
    }
}
