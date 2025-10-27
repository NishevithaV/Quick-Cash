package com.example.quick_cash.job_posting;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.allOf;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    public ActivityScenario<JobPostingActivity> activityScenario;

    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(JobPostingActivity.class);
        activityScenario.onActivity(activity -> {
            activity.loadJobCategorySpinner();
            activity.setupPostJobButton();
        });
    }

    @Test
    public void checkIfJobTitleIsEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText(""));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_JOB_TITLE)));
    }


    @Test
    public void checkIfJobTitleIsNotEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Software Engineer"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfJobCategoryIsValid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }


    @Test
    public void checkIfJobCategoryIsInvalid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onData(allOf(is(instanceOf(String.class)), is("Select job category"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.INVALID_JOB_CATEGORY)));
    }

    @Test
    public void checkIfApplicationDeadlineIsEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText(""));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_DEADLINE)));
    }

    @Test
    public void checkIfApplicationDeadlineIsValid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    @Test
    public void checkIfApplicationDeadlineIsInvalid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2024-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.INVALID_APPLICATION_DEADLINE)));
    }

    @Test
    public void checkIfDescriptionIsEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText(""));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_DESCRIPTION)));
    }

    @Test
    public void checkIfDescriptionIsNotEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("We need an AI backend engineer"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }
}
