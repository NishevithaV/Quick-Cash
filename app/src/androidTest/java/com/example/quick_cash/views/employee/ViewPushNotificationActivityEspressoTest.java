package com.example.quick_cash.views.employee;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ViewPushNotificationActivityEspressoTest {


    @Test
    public void checkInjectedDataDisplayed() {
        Intent intent = new Intent();
        intent.putExtra("title", "Test Job Title");
        intent.putExtra("body", "This is a test body");
        intent.putExtra("job_id", "12345");
        intent.putExtra("jobLocation", "Halifax, NS");

        ActivityScenario<ViewPushNotificationActivity> scenario =
                ActivityScenario.launch(intent.setClassName(
                        "com.example.quick_cash",
                        "com.example.quick_cash.views.employee.ViewPushNotificationActivity"
                ));

        onView(withId(R.id.titleTV)).check(matches(withText("Test Job Title")));
        onView(withId(R.id.bodyTV)).check(matches(withText("This is a test body")));
        onView(withId(R.id.jobLocationTV)).check(matches(withText("Halifax, NS")));
        onView(withId(R.id.jobIdTV)).check(matches(withText("Test Job Title")));
    }
}
