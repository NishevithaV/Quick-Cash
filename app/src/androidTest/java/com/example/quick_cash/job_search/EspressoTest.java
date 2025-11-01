package com.example.quick_cash.job_search;

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

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

import com.example.quick_cash.R;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    public ActivityScenario<JobSearchActivity> activityScenario;
    private final String gibberish = "aspfiojhnapsfanhaivhiuhawejj;aijhnhuiawoiefh";

    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(JobSearchActivity.class);
        activityScenario.onActivity(JobSearchActivity::initUI);
    }

    @Test
    public void testSearchUpdatesResults() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(not(isDisplayed())));

        onView(withId(R.id.userSearch)).perform(clearText(), typeText("Dev"), closeSoftKeyboard());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.resultsView)).check(matches(isDisplayed()));
    }

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

    @Test
    public void testNoResultsMessage() {
        onView(withId(R.id.userSearch)).perform(typeText(gibberish), closeSoftKeyboard());
        onView(withId(R.id.searchBtn)).perform(click());
        onView(withId(R.id.textViewResHead)).check(matches(withText(R.string.NO_RESULT)));
    }

}