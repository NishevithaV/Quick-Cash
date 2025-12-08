package com.example.quick_cash.views.employer;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;

import android.content.Intent;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Job;
import com.example.quick_cash.views.employee.EmployeeApplicationsActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * The type Employer listings espresso test.
 */
public class EmployerListingsEspressoTest {

    /**
     * The Activity scenario.
     */
    public ActivityScenario<EmployerListingsActivity> activityScenario;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), EmployerListingsActivity.class);
        intent.putExtra("userID", "testUserID");
        intent.putExtra("isTest", true);
        activityScenario = ActivityScenario.launch(intent);
    }

    /**
     * Helper function to pause and wait for an amount of time
     *
     * @param millis time to wait for
     * @return ViewAction
     */
    public static ViewAction waitFor(long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    /**
     * Check if no jobs posted.
     */
// test just for checking job list features
    @Test
    public void checkIfNoJobsPosted() {
        ArrayList<Job> jobs = new ArrayList<>();
        activityScenario.onActivity(activity -> {
           activity.setDisplayedJobsForTest(jobs);
        });

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.dashboardTitle)).check(matches(withText("Your Posted Jobs")));

        onData(is("No jobs posted yet.")).inAdapterView(withId(R.id.jobListView))
                .check(matches(isDisplayed()));
    }

    /**
     * Test that once a job is clicked the employer can see all applications for it.
     */
    @Test
    public void testClickJobOpensApplications() {
        ArrayList<Job> jobs = new ArrayList<>();
        jobs.add(new Job("Test Title",
                "Test Category",
                "2025-12-25",
                "Test job description",
                "testUserID",
                "testJobID1"));
        activityScenario.onActivity(activity -> {
            activity.setDisplayedJobsForTest(jobs);
        });

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.jobListView)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.jobListView)).atPosition(0)
                .check(matches(withText("Test Title")));
        onData(anything()).inAdapterView(withId(R.id.jobListView)).atPosition(0).perform(click());
        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.viewAppsHeading)).check(matches(withText("Test Title Applications")));
    }
}
