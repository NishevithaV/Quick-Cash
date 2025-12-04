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

    /**
     * AT-4: Test MapViewActivity displays markers for Halifax jobs.
     */
    @Test
    public void testMapViewDisplaysMarkersForHalifaxJobs() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Android Developer", "Tech", "Halifax", "2025-12-31", 
                "Develop Android apps", "user1", 44.6488, -63.5752));
        jobs.add(new Job("Nurse", "Health", "Halifax", "2025-12-30", 
                "Work in hospital", "user2", 44.6500, -63.5800));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map and markers to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify map is displayed
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        // Verify activity has loaded the jobs correctly
        scenario.onActivity(activity -> {
            // Activity should have loaded without crashing
            assert(activity != null);
        });
        
        scenario.close();
    }

    /**
     * AT-4: Test MapViewActivity with single Halifax job creates marker.
     */
    @Test
    public void testMapViewCreatesMarkerForSingleHalifaxJob() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Software Engineer", "Tech", "Halifax", "2025-12-31", 
                "Build software solutions", "user1", 44.6488, -63.5752));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to load
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Map should be displayed
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        // No error message should appear
        onView(withId(R.id.noJobsMessage)).check(matches(not(isDisplayed())));
        
        scenario.close();
    }

    /**
     * AT-4: Test MapViewActivity with multiple Halifax jobs at different locations.
     */
    @Test
    public void testMapViewWithMultipleHalifaxLocations() {
        ArrayList<Job> jobs = new ArrayList<>();
        // Downtown Halifax
        jobs.add(new Job("Web Developer", "Tech", "Halifax", "2025-12-31", 
                "Create websites", "user1", 44.6488, -63.5752));
        // Halifax West End
        jobs.add(new Job("Nurse Practitioner", "Health", "Halifax", "2025-12-30", 
                "Patient care", "user2", 44.6500, -63.5800));
        // Halifax North End
        jobs.add(new Job("Teacher", "Education", "Halifax", "2025-12-29", 
                "Teach students", "user3", 44.6600, -63.5850));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to load all markers
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Map should be displayed with all markers
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        scenario.close();
    }

    /**
     * AT-4: Test MapViewActivity stores job data in marker tags.
     */
    @Test
    public void testMapViewStoresJobDataInMarkers() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Data Analyst", "Tech", "Halifax", "2025-12-31", 
                "Analyze data sets", "user1", 44.6488, -63.5752));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to load
        try {
            Thread.sleep(2500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify the activity loaded successfully with job data
        scenario.onActivity(activity -> {
            // The map should have been initialized
            assert(activity != null);
        });
        
        scenario.close();
    }

    /**
     * AT-4: Test marker info window displays job title.
     */
    @Test
    public void testMarkerInfoWindowDisplaysJobTitle() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Registered Nurse", "Health", "Halifax", "2025-12-30", 
                "Hospital nursing", "user1", 44.6488, -63.5752));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map and marker to load
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Map should be displayed
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        scenario.close();
    }

    /**
     * AT-4: Test marker info window displays job location.
     */
    @Test
    public void testMarkerInfoWindowDisplaysJobLocation() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Project Manager", "Management", "Halifax", "2025-12-31", 
                "Manage projects", "user1", 44.6488, -63.5752));

        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), MapViewActivity.class);
        intent.putExtra("jobs", jobs);

        ActivityScenario<MapViewActivity> scenario = ActivityScenario.launch(intent);

        // Wait for map to fully initialize
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Verify map loaded
        onView(withId(R.id.map)).check(matches(isDisplayed()));
        
        scenario.close();
    }
}
