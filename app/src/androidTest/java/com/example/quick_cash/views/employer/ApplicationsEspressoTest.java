package com.example.quick_cash.views.employer;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ActivityScenario;

import com.example.quick_cash.R;
import com.example.quick_cash.views.employer.ApplicationsActivity;

import org.junit.Before;
import org.junit.Test;

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
        onView(withId(R.id.appsResultsView)).check(matches(isDisplayed()));
    }

}
