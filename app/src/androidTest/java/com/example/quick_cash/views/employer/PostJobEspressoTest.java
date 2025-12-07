package com.example.quick_cash.views.employer;

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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.example.quick_cash.views.employer.PostFormActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

@RunWith(AndroidJUnit4.class)
public class PostJobEspressoTest {

    public ActivityScenario<PostFormActivity> activityScenario;

    private Jobs mockJobs;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        mockJobs = Mockito.mock(Jobs.class);

        // Make postJob return a fake jobId (or true if it returns boolean)
        when(mockJobs.postJob(any())).thenReturn("fakeJobId");

        activityScenario = ActivityScenario.launch(PostFormActivity.class);
        activityScenario.onActivity(activity -> {
            activity.setJobsCRUD(mockJobs);
            activity.loadJobCategorySpinner();
            activity.setupPostJobButton();
        });
    }

    /**
     * Check if job title is empty.
     */
    @Test
    public void checkIfJobTitleIsEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText(""));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_JOB_TITLE)));
    }


    /**
     * Check if job title is not empty.
     */
    @Test
    public void checkIfJobTitleIsNotEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Software Engineer"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-12-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    /**
     * Check if job category is valid.
     */
    @Test
    public void checkIfJobCategoryIsValid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-12-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }


    /**
     * Check if job category is invalid.
     */
    @Test
    public void checkIfJobCategoryIsInvalid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Select job category"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.INVALID_JOB_CATEGORY)));
    }

    /**
     * Check if application deadline is empty.
     */
    @Test
    public void checkIfApplicationDeadlineIsEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText(""));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_DEADLINE)));
    }

    /**
     * Check if application deadline is valid.
     */
    @Test
    public void checkIfApplicationDeadlineIsValid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-12-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    /**
     * Check if application deadline is invalid.
     */
    @Test
    public void checkIfApplicationDeadlineIsInvalid() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2024-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.INVALID_APPLICATION_DEADLINE)));
    }

    /**
     * Check if description is empty.
     */
    @Test
    public void checkIfDescriptionIsEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-12-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText(""));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_DESCRIPTION)));
    }

    /**
     * Check if description is not empty.
     */
    @Test
    public void checkIfDescriptionIsNotEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-12-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("We need an AI backend engineer"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }

    /**
     * Check if location field is empty.
     */
    @Test
    public void checkIfLocationIsEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText(""));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-11-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("Sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_LOCATION)));
    }

    /**
     * Check if location field is not empty.
     */
    @Test
    public void checkIfLocationIsNotEmpty() {
        onView(withId(R.id.JobTitleField)).perform(typeText("Backend Dev"));
        onView(withId(R.id.JobCategorySpinner)).perform(click());
        onData(allOf(is(instanceOf(String.class)), is("AI"))).perform(click());
        onView(withId(R.id.JobLocationField)).perform(typeText("Halifax, NS"));
        onView(withId(R.id.ApplicationDeadlineField)).perform(typeText("2025-12-21"));
        onView(withId(R.id.JobDescriptionField)).perform(typeText("Sample description"));
        onView(withId(R.id.PostJobButton)).perform(click());
        onView(withId(R.id.Result)).check(matches(withText(R.string.EMPTY_STRING)));
    }
}
