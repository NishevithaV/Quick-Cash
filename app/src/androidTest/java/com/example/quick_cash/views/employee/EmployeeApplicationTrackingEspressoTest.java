package com.example.quick_cash.views.employee;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.ToastMatcher;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.not;

/**
 * Espresso test suite for the Employee Application Tracking UI (US-12).
 * This suite validates:
 * - RecyclerView visibility and rendering
 * - Status badge visibility (Pending, Accepted, Completed, Rejected, Paid)
 * - Conditional visibility of the "Mark Completed" button
 * - Successful UI update after clicking "Mark Completed"
 * - Toast notification on completion
 */
@RunWith(AndroidJUnit4.class)
public class EmployeeApplicationTrackingEspressoTest {

    @Rule
    public ActivityScenarioRule<EmployeeApplicationTrackingActivity> activityRule =
            new ActivityScenarioRule<>(EmployeeApplicationTrackingActivity.class);

    /**
     * Verifies that the RecyclerView displaying the employee's applications is visible.
     */
    @Test
    public void testApplicationListIsDisplayed() {
        onView(withId(R.id.recycler_applications))
                .check(matches(isDisplayed()));
    }

    /**
     * Ensures that status badges (Rejected, Completed, Accepted, Paid) are visible
     * for corresponding application items.
     * This checks correct rendering of application states as shown in your mock UI.
     */
    @Test
    public void testStatusBadgesAreVisible() {
        onView(withText("Rejected"))
                .check(matches(isDisplayed()));
        onView(withText("Completed"))
                .check(matches(isDisplayed()));
        onView(withText("Accepted"))
                .check(matches(isDisplayed()));
        onView(withText("Paid"))
                .check(matches(isDisplayed()));
    }

    /**
     * Verifies the "Mark Completed" button is only shown when the status is "Accepted".
     * Ensures:
     * Accepted → button visible
     * Pending/Rejected/Completed/Paid → button not visible
     */
    @Test
    public void testMarkCompletedButtonVisibleOnlyForAcceptedJobs() {

        // Button should appear next to the Accepted badge
        onView(allOf(withId(R.id.btn_mark_completed),
                hasSibling(withText("Accepted"))))
                .check(matches(isDisplayed()));

        // Should NOT appear for Rejected
        onView(allOf(withId(R.id.btn_mark_completed),
                hasSibling(withText("Rejected"))))
                .check(matches(not(isDisplayed())));

        // Should NOT appear for Completed
        onView(allOf(withId(R.id.btn_mark_completed),
                hasSibling(withText("Completed"))))
                .check(matches(not(isDisplayed())));

        // Should NOT appear for Paid
        onView(allOf(withId(R.id.btn_mark_completed),
                hasSibling(withText("Paid"))))
                .check(matches(not(isDisplayed())));
    }

    /**
     * Simulates clicking the "Mark Completed" button for an accepted job,
     * then checks if the status updates to "Completed".
     * This validates correct UI refresh after status update.
     */
    @Test
    public void testMarkCompletedUpdatesStatus() {

        // Click the button next to an Accepted job
        onView(allOf(withId(R.id.btn_mark_completed),
                hasSibling(withText("Accepted"))))
                .perform(click());

        // Status should now show "Completed"
        onView(withText("Completed"))
                .check(matches(isDisplayed()));
    }

    /**
     * Validates that a toast with "Marked as completed" appears after the user
     * marks an accepted job as completed.
     * This test uses a custom ToastMatcher
     */
    @Test
    public void testCompletionToastAppears() {

        // Trigger completion
        onView(allOf(withId(R.id.btn_mark_completed),
                hasSibling(withText("Accepted"))))
                .perform(click());

        // Expect toast
        onView(withText("Marked as completed"))
                .inRoot(new ToastMatcher())
                .check(matches(isDisplayed()));
    }
}
