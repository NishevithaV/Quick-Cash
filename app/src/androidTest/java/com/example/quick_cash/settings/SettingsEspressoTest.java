package com.example.quick_cash.settings;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.quick_cash.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class SettingsEspressoTest {

    @Rule
    public ActivityScenarioRule<SettingsActivity> activityRule =
            new ActivityScenarioRule<>(SettingsActivity.class);

    @Test
    public void testSwitchRoleButtonDisplayed() {
        onView(withId(R.id.switchRoleButton))
                .check(matches(isDisplayed()));
    }

    @Test
    public void testConfirmActivityLaunch() {
        onView(withId(R.id.switchRoleButton)).perform(click());
        onView(withId(R.id.roleConfirmButton)).check(matches(isDisplayed()));
    }
}