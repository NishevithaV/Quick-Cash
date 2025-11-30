package com.example.quick_cash.views.employee;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.example.quick_cash.views.employee.JobDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import android.content.Context;
import android.content.Intent;

import com.example.quick_cash.R;

@RunWith(AndroidJUnit4.class)
public class JobDetailsEspressoTest {

    @Rule
    public ActivityScenarioRule<JobDetailActivity> rule =
            new ActivityScenarioRule<>(createIntent());

    private Intent createIntent() {
        Context ctx = ApplicationProvider.getApplicationContext();
        Intent i = new Intent(ctx, JobDetailActivity.class);
        i.putExtra("jobID", "123");
        i.putExtra("title", "Test Title");
        i.putExtra("employer", "Test Employer");
        i.putExtra("category", "Cleaning");
        i.putExtra("description", "Test description");
        return i;
    }

    @Test
    public void testAllViewsVisible() {
        onView(withId(R.id.jobTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.jobEmployer)).check(matches(isDisplayed()));
        onView(withId(R.id.jobCategory)).check(matches(isDisplayed()));
        onView(withId(R.id.jobDesc)).check(matches(isDisplayed()));
        onView(withId(R.id.applyButton)).check(matches(isDisplayed()));
    }
}

