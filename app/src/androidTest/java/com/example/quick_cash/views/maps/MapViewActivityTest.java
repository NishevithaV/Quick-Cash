package com.example.quick_cash.views.maps;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Job;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class MapViewActivityTest {

    /**
     * AT-3/AT-4: Test MapViewActivity launches with jobs.
     */
    @Test
    public void testMapViewActivityLaunchesWithJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Android Developer", "Tech", "Halifax", "2025-12-31", 
                "Develop Android apps", "user1", 44.6488, -63.5752));
        jobs.add(new Job("Nurse", "Health", "Halifax", "2025-12-30", 
                "Work in hospital", "user2", 44.6500, -63.5800));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Map should be displayed
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        // No jobs message should NOT be visible
        onView(withId(R.id.noJobsMessage)).check(matches(not(isDisplayed())));
        
        scenario.close();
    }

    /**
     * AT-5: Test MapViewActivity shows message when no jobs have coordinates.
     */
    @Test
    public void testMapViewShowsNoJobsMessage() {
        ArrayList<Job> jobs = new ArrayList<>();
        // Jobs without coordinates
        jobs.add(new Job("Remote Job", "Tech", "Remote", "2025-12-31", 
                "Work from home", "user1"));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for activity to process
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // No jobs message should be visible
        onView(withId(R.id.noJobsMessage)).check(matches(isDisplayed()));
        onView(withId(R.id.noJobsMessage)).check(matches(withText("No jobs found in this location")));
        
        scenario.close();
    }

    /**
     * AT-5: Test MapViewActivity shows message when jobs list is empty.
     */
    @Test
    public void testMapViewShowsMessageForEmptyList() {
        ArrayList<Job> jobs = new ArrayList<>(); // Empty list

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for activity to process
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // No jobs message should be visible
        onView(withId(R.id.noJobsMessage)).check(matches(isDisplayed()));
        
        scenario.close();
    }

    /**
     * AT-3: Test MapViewActivity filters jobs without coordinates.
     */
    @Test
    public void testMapViewFiltersJobsWithoutCoordinates() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Android Developer", "Tech", "Halifax", "2025-12-31", 
                "Develop Android apps", "user1", 44.6488, -63.5752)); // Has coordinates
        jobs.add(new Job("Remote Job", "Tech", "Remote", "2025-12-31", 
                "Work from home", "user2")); // No coordinates

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Map should display (because at least one job has coordinates)
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        // No jobs message should NOT be visible
        onView(withId(R.id.noJobsMessage)).check(matches(not(isDisplayed())));
        
        scenario.close();
    }

    /**
     * AT-3: Test MapViewActivity handles single job with coordinates.
     */
    @Test
    public void testMapViewHandlesSingleJob() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Math Teacher", "Education", "Toronto", "2025-12-15", 
                "Teach math", "user3", 43.6532, -79.3832));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Map should be displayed
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        // No jobs message should NOT be visible
        onView(withId(R.id.noJobsMessage)).check(matches(not(isDisplayed())));
        
        scenario.close();
    }

    /**
     * AT-3: Test MapViewActivity handles multiple jobs with coordinates.
     */
    @Test
    public void testMapViewHandlesMultipleJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Android Developer", "Tech", "Halifax", "2025-12-31", 
                "Develop Android apps", "user1", 44.6488, -63.5752));
        jobs.add(new Job("Nurse", "Health", "Halifax", "2025-12-30", 
                "Work in hospital", "user2", 44.6500, -63.5800));
        jobs.add(new Job("Math Teacher", "Education", "Toronto", "2025-12-15", 
                "Teach math", "user3", 43.6532, -79.3832));
        jobs.add(new Job("English Teacher", "Education", "Calgary", "2025-12-15", 
                "Teach English", "user4", 51.0447, -114.0719));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Map should be displayed with all markers
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        // No jobs message should NOT be visible
        onView(withId(R.id.noJobsMessage)).check(matches(not(isDisplayed())));
        
        scenario.close();
    }
}
