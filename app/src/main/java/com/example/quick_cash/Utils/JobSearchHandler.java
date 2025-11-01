package com.example.quick_cash.Utils;


import com.example.quick_cash.Models.Job;

import java.util.ArrayList;
import java.util.List;

public class JobSearchHandler {
    private ArrayList<Job> allJobs;
    public JobSearchHandler(List<Job> jobs) {
        updateAllJobs(jobs);
    }

    private void updateAllJobs(List<Job> sampleJobs) {
        allJobs = new ArrayList<>(sampleJobs);
    }

    public ArrayList<Job> getAllJobs(String search) {
        if (search.isEmpty()) return allJobs;
        else {
            ArrayList<Job> results = new ArrayList<>();

            return results;
        }
    }
}
