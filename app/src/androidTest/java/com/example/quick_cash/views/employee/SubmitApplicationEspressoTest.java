package com.example.quick_cash.views.employee;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.views.employee.SubmitApplicationActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Context;
import android.content.Intent;

@RunWith(AndroidJUnit4.class)
public class SubmitApplicationEspressoTest {

    @Rule
    public ActivityScenarioRule<SubmitApplicationActivity> rule =
            new ActivityScenarioRule<>(createIntent());

    private Intent createIntent() {
        Context ctx = ApplicationProvider.getApplicationContext();
        Intent i = new Intent(ctx, SubmitApplicationActivity.class);
        i.putExtra("jobID", "123");
        return i;
    }

    @Test
    public void testAllViewsVisible() {
        onView(withId(R.id.cvrLtrInput)).check(matches(isDisplayed()));
        onView(withId(R.id.submitLtrBtn)).check(matches(isDisplayed()));
    }
}

