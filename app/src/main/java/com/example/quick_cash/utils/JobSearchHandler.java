package com.example.quick_cash.utils;

import android.location.Location;
import android.util.Log;

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

    /**
     * Updates the new Job search handler.
     *
     * @param sampleJobs the sample jobs
     */
    private void updateAllJobs(List<Job> sampleJobs) {
        allJobs = new ArrayList<>(sampleJobs);
    }

    /**
     * Match search boolean.
     *
     * @param job the job
     * @param search the search
     * @return the boolean
     */
    private boolean matchSearch(Job job, String search) {
        if (search == null || search.isEmpty()) return true;

        search = search.toLowerCase();

        return job.getTitle().toLowerCase().contains(search) ||
                job.getCategory().toLowerCase().contains(search) ||
                job.getDesc().toLowerCase().contains(search);
    }

    /**
     * Match category boolean.
     *
     * @param job the job
     * @param category the category
     * @return the boolean
     */
    private boolean matchCategory(Job job, String category) {
        if (category == null || category.isEmpty() || category.equals("Category")) {
            return true;
        }

        return job.getCategory().equalsIgnoreCase(category);
    }

    /**
    * Match location boolean.
    *
    * @param job the job
    * @param locationOption the location option
    * @param userLocation the user location
     * @return the boolean
    */
    private boolean matchLocation(Job job, String locationOption, String userLocation) {
        if (locationOption == null || locationOption.equals("All Jobs")) {
            return true;
        }

        if (locationOption.equals("Nearby Jobs")) {
            if (job.getLocation() == null || job.getLocation().equals("null"))
                return false;

            if (userLocation == null)
                return true;

            String jobLoc = job.getLocation().toLowerCase().trim();
            String userLoc = userLocation.toLowerCase().trim();

            String[] parts = userLoc.split(", ");
            String firstPart = parts.length > 0 ? parts[0] : userLoc;
            return userLoc.contains(jobLoc) || jobLoc.contains(firstPart);
        }

        return true;
    }

    /**
     * Match category and search boolean.
     *
     * @param job the job
     * @param search the search
     * @param category the category
     * @return the boolean
     */
    private boolean matchCategoryAndSearch(Job job, String search, String category) {
        return matchSearch(job, search) && matchCategory(job, category);
    }

    // TODO: Bro why is this method here, are we even using it anywhere?
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
    public ArrayList<Job> getAllJobs(String search,
                                     String category,
                                     String locationOption,
                                     String userLocation) {
        ArrayList<Job> results = new ArrayList<>();

        for (Job job : allJobs) {

            if (!matchSearch(job, search)) {
                continue;
            }
            if (!matchCategory(job, category)) {
                continue;
            }
            if (!matchLocation(job, locationOption, userLocation)) {
                continue;
            }
            if (job.getLocation() != null && !job.getLocation().equals("null"))
                results.add(job);
        }

        return results;
    }

    /**
     * Calculate distance between two coordinates using Android Location API.
     *
     * @param lat1 the first latitude
     * @param lng1 the first longitude
     * @param lat2 the second latitude
     * @param lng2 the second longitude
     * @return distance in kilometers
     */
    private float calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lng1, lat2, lng2, results);
        return results[0] / 1000; // Convert meters to kilometers
    }

    /**
     * Gets jobs filtered by location radius.
     *
     * @param search      the search text
     * @param category    the category
     * @param searchLat   the search latitude
     * @param searchLng   the search longitude
     * @param radiusKm    the radius in kilometers
     * @return the filtered jobs
     */
    public ArrayList<Job> getJobsByLocationRadius(String search, String category, 
                                                   double searchLat, double searchLng, 
                                                   int radiusKm) {
        ArrayList<Job> results = new ArrayList<>();

        for (Job job : allJobs) {
            // Check search and category first
            if (!matchSearch(job, search)) {
                continue;
            }
            if (!matchCategory(job, category)) {
                continue;
            }

            // Check if job has coordinates
            if (job.getLatitude() == 0.0 && job.getLongitude() == 0.0) {
                continue; // Skip jobs without coordinates
            }

            // Calculate distance
            float distance = calculateDistance(
                    searchLat, searchLng, 
                    job.getLatitude(), job.getLongitude()
            );

            // Include if within radius
            if (distance <= radiusKm) {
                results.add(job);
            }
        }

        return results;
    }
}
