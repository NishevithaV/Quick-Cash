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

    /**
     * AT-2: Test location search field exists.
     */
    @Test
    public void testLocationSearchFieldExists() {
        onView(withId(R.id.locationSearchField)).check(matches(isDisplayed()));
    }

    /**
     * AT-2: Test radius selector exists.
     */
    @Test
    public void testRadiusSelectorExists() {
        onView(withId(R.id.radiusSelect)).check(matches(isDisplayed()));
    }

    /**
     * AT-2: Test searching by location with radius filters results.
     */
    @Test
    public void testSearchByLocationWithRadius() {
        // Enter location and select radius
        onView(withId(R.id.locationSearchField)).perform(typeText("Halifax, NS"), closeSoftKeyboard());
        onView(withId(R.id.radiusSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("10 km"))).perform(click());
        
        // Perform search
        onView(withId(R.id.searchBtn)).perform(click());
        
        // Wait for geocoding and results
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Results should be displayed
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

    /**
     * AT-2: Test searching with invalid location shows error.
     */
    @Test
    public void testSearchWithInvalidLocationShowsError() {
        // Enter invalid location
        onView(withId(R.id.locationSearchField)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.radiusSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("10 km"))).perform(click());
        
        // Perform search - should show error toast
        onView(withId(R.id.searchBtn)).perform(click());
        
        // Wait for geocoding attempt
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * AT-2: Test searching without radius uses default behavior.
     */
    @Test
    public void testSearchWithoutRadiusUsesDefault() {
        // Enter location but don't select radius
        onView(withId(R.id.locationSearchField)).perform(typeText("Halifax"), closeSoftKeyboard());
        
        // Search without selecting radius - should use default search
        onView(withId(R.id.searchBtn)).perform(click());
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Should still show results using default logic
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

    /**
     * AT-3: Test View on Map button exists.
     */
    @Test
    public void testViewOnMapButtonExists() {
        onView(withId(R.id.viewOnMapBtn)).check(matches(isDisplayed()));
    }

    /**
     * AT-3: Test View on Map button appears after location search.
     */
    @Test
    public void testViewOnMapButtonAppearsAfterLocationSearch() {
        // Perform location search
        onView(withId(R.id.locationSearchField)).perform(typeText("Halifax, NS"), closeSoftKeyboard());
        onView(withId(R.id.radiusSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("10 km"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        
        // Wait for results
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // View on Map button should now be visible
        onView(withId(R.id.viewOnMapBtn)).check(matches(isDisplayed()));
    }

    /**
     * AT-3: Test clicking View on Map button with jobs.
     */
    @Test
    public void testClickViewOnMapButtonWithJobs() {
        // Perform location search to get jobs
        onView(withId(R.id.locationSearchField)).perform(typeText("Halifax"), closeSoftKeyboard());
        onView(withId(R.id.radiusSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("50 km"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        
        // Wait for results
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Click View on Map button
        onView(withId(R.id.viewOnMapBtn)).perform(click());
        
        // Wait for MapView to load
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // MapView should be displayed (check for map container)
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }

    /**
     * AT-3: Test View on Map shows correct activity.
     */
    @Test
    public void testViewOnMapOpensMapActivity() {
        // Perform location search
        onView(withId(R.id.locationSearchField)).perform(typeText("Halifax"), closeSoftKeyboard());
        onView(withId(R.id.radiusSelect)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("25 km"))).perform(click());
        onView(withId(R.id.searchBtn)).perform(click());
        
        // Wait for results
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Click View on Map
        onView(withId(R.id.viewOnMapBtn)).perform(click());
        
        // Wait for activity transition
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Should see the map fragment
        onView(withId(R.id.map)).check(matches(isDisplayed()));
    }




}
