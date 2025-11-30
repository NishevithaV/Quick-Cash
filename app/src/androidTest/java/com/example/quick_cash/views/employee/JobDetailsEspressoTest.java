package com.example.quick_cash.views.employee;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static org.hamcrest.CoreMatchers.not;

import android.content.Context;
import android.content.Intent;

import com.example.quick_cash.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

@RunWith(AndroidJUnit4.class)
public class JobDetailsEspressoTest {

    private ActivityScenario<JobDetailActivity> scenario;
    @Before
    public void setup() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            FirebaseAuth.getInstance().signOut();
        }

    }
    private Intent createIntent(String id) {
        Context ctx = ApplicationProvider.getApplicationContext();
        Intent i = new Intent(ctx, JobDetailActivity.class);
        i.putExtra("jobID", id);
        i.putExtra("title", "Test Title");
        i.putExtra("employer", "Test Employer");
        i.putExtra("category", "Cleaning");
        i.putExtra("description", "Test description");
        return i;
    }

    @Test
    public void testAllViewsVisible() {
        scenario = ActivityScenario.launch(createIntent("123"));
        onView(withId(R.id.jobTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.jobEmployer)).check(matches(isDisplayed()));
        onView(withId(R.id.jobCategory)).check(matches(isDisplayed()));
        onView(withId(R.id.jobDesc)).check(matches(isDisplayed()));
        onView(withId(R.id.applyButton)).check(matches(isDisplayed()));
    }

    @Test
    public void applyButtonClick_opensSubmitActivity() {
        scenario = ActivityScenario.launch(createIntent("123"));
        onView(withId(R.id.applyButton)).perform(click());
        onView(withId(R.id.cvrLtrHead)).check(matches(isDisplayed()));
    }

    @Test
    public void testOpenAlreadyAppliedJob() throws InterruptedException{
        scenario = ActivityScenario.launch(createIntent("testJobIDalreadyapplied"));
        onView(withId(R.id.applyButton)).check(matches(not(isDisplayed())));
        onView(withId(R.id.jobDetailAlreadyApplied)).check(matches(isDisplayed()));
    }
}

