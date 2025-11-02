package com.example.quick_cash.utils;


import com.example.quick_cash.models.Job;

import java.util.ArrayList;
import java.util.List;

public class JobSearchHandler {
    private ArrayList<Job> allJobs;

    /**
     * Instantiates a new Job search handler.
     *
     * @param jobs the jobs
     */
    public JobSearchHandler(List<Job> jobs) {
        updateAllJobs(jobs);
    }

    private void updateAllJobs(List<Job> sampleJobs) {
        allJobs = new ArrayList<>(sampleJobs);
    }

    private boolean matchSearch(Job j, String search) {
        return j.getTitle().toLowerCase().contains(search.toLowerCase()) ||
                j.getCategory().toLowerCase().contains(search.toLowerCase()) ||
                j.getDesc().toLowerCase().contains(search.toLowerCase());
    }

    private boolean matchCategory(Job j, String category) {
        return j.getCategory().toLowerCase().contains(category.toLowerCase());
    }

    private boolean matchCategoryAndSearch(Job j, String search, String category) {
        return matchSearch(j, search) && matchCategory(j, category);
    }

    private ArrayList<Job> filterJobs(String search, String category) {
        ArrayList<Job> results = new ArrayList<>();
        boolean categoryIsEmpty = (category.isEmpty() || category.equals("Category"));

        if (!search.isEmpty() && categoryIsEmpty) {
            for (Job j : allJobs) {
                if (matchSearch(j, search)) {
                    results.add(j);
                }
            }
        } else if (!categoryIsEmpty && search.isEmpty()) {
            for (Job j : allJobs) {
                if (matchCategory(j, category)) {
                    results.add(j);
                }
            }
        } else {
            for (Job j : allJobs) {
                if (matchCategoryAndSearch(j, search, category)) {
                    results.add(j);
                };
            }
        }

        return results;
    }

    /**
     * Gets all jobs.
     *
     * @param search   the search
     * @param category the category
     * @return the all jobs
     */
    public ArrayList<Job> getAllJobs(String search, String category) {
        boolean categoryIsEmpty = (category.isEmpty() || category.equals("Category"));
        if (search.isEmpty() && categoryIsEmpty) {
            return allJobs;
        }
        else {
            return filterJobs(search, category);
        }
    }
}
