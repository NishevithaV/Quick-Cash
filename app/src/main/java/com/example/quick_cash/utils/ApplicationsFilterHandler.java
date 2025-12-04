package com.example.quick_cash.utils;


import com.example.quick_cash.models.Application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationsFilterHandler {
    private ArrayList<Application> allJobs;

    /**
     * Instantiates a new Application search handler.
     *
     * @param apps the jobs
     */
    public ApplicationsFilterHandler(List<Application> apps) {
        updateAllApps(apps);
    }

    private void updateAllApps(List<Application> sampleApps) {
        allJobs = new ArrayList<>(sampleApps);
    }

    private boolean matchStatus(Application a, String status) {
        return a.getStatus().equalsIgnoreCase(status);
    }
    private ArrayList<Application> filterApps(String search) {
        ArrayList<Application> results = new ArrayList<>();

        for (Application j : allJobs) {
            if (matchStatus(j, search)) {
                results.add(j);
            }
        }

        return results;
    }

    /**
     * Gets all jobs.
     *
     * @param status   the search
     * @return the matching apps
     */
    public ArrayList<Application> getApps(String status) {
        return filterApps(status);
    }
}
