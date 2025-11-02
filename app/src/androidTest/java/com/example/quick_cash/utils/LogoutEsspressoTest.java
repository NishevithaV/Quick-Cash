package com.example.quick_cash.utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.views.settings.SettingsActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogoutEsspressoTest {

    /**
     * The Activity scenario.
     */
    public ActivityScenario<SettingsActivity> activityScenario;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(SettingsActivity.class);
    }

    /**
     * Check if logout button works.
     */
    @Test
    public void checkIfLogoutButtonWorks() {
        onView(withId(R.id.logoutButton)).perform(click());
        onView(withText("Confirm Logout")).check(matches(isDisplayed()));
        onView(withText("Yes")).check(matches(isDisplayed()));
        onView(withText("Cancel")).check(matches(isDisplayed()));
    }

    /**
     * Check if cancel button works.
     */
    @Test
    public void checkIfCancelButtonWorks() {
        onView(withId(R.id.logoutButton)).perform(click());
        onView(withText("Cancel")).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Settings")));
    }

    /**
     * Check if confirm button works.
     */
    @Test
    public void checkIfConfirmButtonWorks() {
        onView(withId(R.id.logoutButton)).perform(click());
        onView(withText("Yes")).perform(click());
        onView(withText("Login")).check(matches(isDisplayed()));
    }
}
