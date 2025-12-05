package com.example.quick_cash.views.employer;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.matcher.RootMatchers;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;
import com.example.quick_cash.views.employer.ApplicationsActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ApplicationsEspressoTest {

    /**
     * The Activity scenario.
     */
    public ActivityScenario<ApplicationsActivity> activityScenario;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(ApplicationsActivity.class);
    }

    /**
     * Test applications load
     */
    @Test
    public void testApplicationsLoad() {
        FirebaseAuth.getInstance().signOut();
        onView(withId(R.id.appsResultsView)).check(matches(isDisplayed()));
    }

    @Test
    public void acceptButtonShowsToast() {
        Intent intent = new Intent(
                androidx.test.core.app.ApplicationProvider.getApplicationContext(),
                ApplicationReviewActivity.class
        );
        intent.putExtra("letter", "Test Letter");
        intent.putExtra("applicantName", "Alice");
        intent.putExtra("status", "pending");
        intent.putExtra("jobTitle", "Designer");
        intent.putExtra("appId", "app001");

        ActivityScenario<ApplicationReviewActivity> scenario =
                ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {
            activity.toastMsg = ""; // reset before test
        });

        onView(withId(R.id.acceptBtn)).perform(click());

        // Verify Toast text
        scenario.onActivity(activity -> {
            String value = activity.toastMsg;
            assertEquals("Application successfully accepted", value);
        });
    }

    @Test
    public void declineButtonShowsToast() {
        Intent intent = new Intent(
                androidx.test.core.app.ApplicationProvider.getApplicationContext(),
                ApplicationReviewActivity.class
        );
        intent.putExtra("letter", "Test Letter");
        intent.putExtra("applicantName", "Alice");
        intent.putExtra("status", "pending");
        intent.putExtra("jobTitle", "Designer");
        intent.putExtra("appId", "app001");

        ActivityScenario<ApplicationReviewActivity> scenario =
                ActivityScenario.launch(intent);

        scenario.onActivity(activity -> {
            activity.toastMsg = ""; // reset before test
        });

        onView(withId(R.id.declineBtn)).perform(click());

        // Verify Toast text
        scenario.onActivity(activity -> {
            String value = activity.toastMsg;
            assertEquals("Application successfully declined", value);
        });
    }
}
