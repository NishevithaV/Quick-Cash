package com.example.quick_cash.log_out;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EsspressoTestLogout {

    public ActivityScenario<SettingsActivity> activityScenario;

    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(SettingsActivity.class);
        activityScenario.onActivity(activity -> {
            activity.initSettingsUI();
        });
    }

    @Test
    public void checkIfCancelButtonWorks() {
        onView(withId(R.id.LogoutButton)).perform(click());
        onView(withId(R.id.CancelButton)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Settings")));
    }

    @Test
    public void checkIfConfirmButtonWorks() {
        onView(withId(R.id.LogoutButton)).perform(click());
        onView(withId(R.id.ConfirmButton)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText("Login")));
    }
}
