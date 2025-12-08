package com.example.quick_cash.views.employee;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.views.employee.SubmitApplicationActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.content.Intent;

/**
 * The type Submit application espresso test.
 */
@RunWith(AndroidJUnit4.class)
public class SubmitApplicationEspressoTest {
    private ActivityScenario<SubmitApplicationActivity> scenario;

    /**
     * Sets .
     */
    @Before
    public void setup() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut();
        }
        scenario = ActivityScenario.launch(createIntent());
    }

    private Intent createIntent() {
        Context ctx = ApplicationProvider.getApplicationContext();
        Intent i = new Intent(ctx, SubmitApplicationActivity.class);
        i.putExtra("jobID", "123");
        return i;
    }

    /**
     * Test all views visible.
     */
    @Test
    public void testAllViewsVisible() {
        onView(withId(R.id.cvrLtrInput)).check(matches(isDisplayed()));
        onView(withId(R.id.submitLtrBtn)).check(matches(isDisplayed()));
    }

    /**
     * Test successful submission toast.
     */
    @Test
    public void testSuccessfulSubmissionToast() {
        onView(withId(R.id.cvrLtrInput)).perform(clearText(), typeText("Cover letter"), closeSoftKeyboard());
        onView(withId(R.id.submitLtrBtn)).perform(click());

        scenario.onActivity(activity -> {
            assertEquals("Application Submitted", activity.lastToastMessage);
        });
    }

    /**
     * Test empty submission toast.
     */
    @Test
    public void testEmptySubmissionToast() {
        onView(withId(R.id.cvrLtrInput)).perform(clearText(), typeText(" "), closeSoftKeyboard());
        onView(withId(R.id.submitLtrBtn)).perform(click());

        scenario.onActivity(activity -> {
            assertEquals("Cover letter empty", activity.lastToastMessage);
        });
    }
}

