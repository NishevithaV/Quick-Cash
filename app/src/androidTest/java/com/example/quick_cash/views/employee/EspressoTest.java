package com.example.quick_cash.views.employee;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

import android.Manifest;

import com.example.quick_cash.R;
import com.example.quick_cash.views.employee.JobSearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    /**
     * The Activity scenario.
     */
    public ActivityScenario<JobSearchActivity> activityScenario;
    private final String gibberish = "aspfiojhnapsfanhaivhiuhawejj;aijhnhuiawoiefh";

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(JobSearchActivity.class);
    }

    /**
     * Test that the current location header updates from the default placeholder text
     */
    @Test
    public void testCurrentLocationHeaderUpdates() {

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.currentLocationHeader)).check(matches(not(withText("Detecting Current Location..."))));
    }

    /**
     * Test search updates results.
     */
    @Test
    public void testSearchUpdatesResults() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(not(isDisplayed())));

        onView(withId(R.id.userSearch)).perform(clearText(), typeText("Dev"), closeSoftKeyboard());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

    /**
     * Test category updates results.
     */
    @Test
    public void testCategoryUpdatesResults() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.catSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Category"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(not(isDisplayed())));

        onView(withId(R.id.userSearch)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.catSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

    /**
     * Test location updates results.
     */
    @Test
    public void testLocationUpdatesResults() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.locationSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("All Jobs"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(not(isDisplayed())));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.userSearch)).perform(clearText(), closeSoftKeyboard());
        onView(withId(R.id.locationSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Nearby Jobs"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

    /**
     * Test category and search update results.
     */
    @Test
    public void testCategoryAndSearchUpdateResults() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.catSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Category"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(not(isDisplayed())));

        onView(withId(R.id.userSearch)).perform(clearText(), typeText("Dev"), closeSoftKeyboard());
        onView(withId(R.id.catSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

    /**
     * Test location and search update results.
     */
    @Test
    public void testLocationAndSearchUpdateResults() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.locationSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("All Jobs"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(not(isDisplayed())));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.userSearch)).perform(clearText(), typeText("data"), closeSoftKeyboard());
        onView(withId(R.id.locationSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Nearby Jobs"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

    /**
     * Test no results message.
     */
    @Test
    public void testNoResultsMessage() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.textViewResHead)).check(matches(withText(R.string.NO_RESULT)));
    }

}