package com.example.quick_cash.job_search;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import com.example.quick_cash.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    public ActivityScenario<JobSearchActivity> activityScenario;

    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(JobSearchActivity.class);
        activityScenario.onActivity(JobSearchActivity::initUI);
    }

    public void testAllJobsDisplayed() {

    }

    @Test
    public void testSearchUpdatesResults() {

    }

    @Test
    public void testNoResultsMessage() {

    }

    @Test
    public void testClickOpensDetail() {

    }
}