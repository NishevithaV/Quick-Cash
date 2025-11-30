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
import com.example.quick_cash.models.Application;
import com.example.quick_cash.models.Job;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class JobApplicationsEspressoTest {

    public ActivityScenario<JobApplicationsActivity> activityScenario;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                JobApplicationsActivity.class
        );

        intent.putExtra("jobID", "testJobID");
        intent.putExtra("jobTitle", "Test Job");
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
     * Test Applications appear in the list when provided.
     */
    @Test
    public void testApplicationsAreVisibleInList() {
        ArrayList<Application> apps = new ArrayList<>();

        apps.add(new Application(
                "applicant1",
                "testJobID",
                "Test cover letter",
                "Pending",
                "app123"
        ));
        apps.add(new Application(
                "applicant2",
                "testJobID",
                "Another letter",
                "Accepted",
                "app456"
        ));

        activityScenario.onActivity(activity -> {
            activity.setDisplayedAppsForTest(apps);
        });

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.appsResultsView)).check(matches(isDisplayed()));

        onData(anything()).inAdapterView(withId(R.id.appsResultsView)).atPosition(0)
                .check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.appsResultsView)).atPosition(1)
                .check(matches(isDisplayed()));
    }
}
