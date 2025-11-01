package com.example.quick_cash.employer;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;

import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;

import com.example.quick_cash.R;

import org.junit.Before;
import org.junit.Test;

public class EmployerDashboardEspressoTest {

    public ActivityScenario<EmployerDashboardActivity> activityScenario;

    @Before
    public void setup() {

        activityScenario = ActivityScenario.launch(EmployerDashboardActivity.class);
        activityScenario.onActivity(activity -> {
            activity.loadJobsForUser("NO_JOB_UID");
        });
    }

    // test just for checking job list features
    @Test
    public void checkIfNoJobsPosted() {

        onView(withId(R.id.dashboardTitle)).check(matches(withText("Your Posted Jobs")));

        onData(is("No jobs posted yet.")).inAdapterView(withId(R.id.jobListView))
                .check(matches(isDisplayed()));
    }
}
