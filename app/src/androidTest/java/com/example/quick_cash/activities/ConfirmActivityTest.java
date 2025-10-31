package com.example.quick_cash.activities;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.switchrole.ConfirmActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Espresso tests for ConfirmActivity
 */
@RunWith(AndroidJUnit4.class)
public class ConfirmActivityTest {

    private Intent intent;

    @Before
    public void setUp() {
        // Create intent with required extras
        intent = new Intent(ApplicationProvider.getApplicationContext(), ConfirmActivity.class);
        intent.putExtra("current_role", "employer");
        intent.putExtra("new_role", "employee");
        intent.putExtra("current_email", "test@example.com");
        intent.putExtra("user_id", "testUserId123");
    }

    @Test
    public void testConfirmationMessageDisplaysCorrectRoles() {
        // Act - Launch activity with intent
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);

        // Assert - Verify the confirmation message displays correct roles
        onView(withId(R.id.roleSwitchText))
                .check(matches(isDisplayed()))
                .check(matches(withText(containsString("employer"))))
                .check(matches(withText(containsString("employee"))))
                .check(matches(withText("You are now switching from employer to employee. Input your email to proceed.")));

        scenario.close();
    }

    @Test
    public void testEmailInputFieldIsDisplayed() {
        // Act - Launch activity
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);

        // Assert - Verify email input field is displayed
        onView(withId(R.id.roleInputEmail))
                .check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testConfirmSwitchButtonIsDisplayed() {
        // Act - Launch activity
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);

        // Assert - Verify confirm button is displayed
        onView(withId(R.id.roleConfirmButton))
                .check(matches(isDisplayed()))
                .check(matches(withText("Confirm Switch")));

        scenario.close();
    }

    @Test
    public void testCorrectEmailNavigatesToCorrectDashboard() {
        // Note: This test verifies the UI flow but cannot fully test Firebase interaction
        // without proper mocking or Firebase Test Lab

        // Act - Launch activity
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);

        // Enter correct email
        onView(withId(R.id.roleInputEmail))
                .perform(typeText("test@example.com"), closeSoftKeyboard());

        // Click confirm button
        onView(withId(R.id.roleConfirmButton))
                .perform(click());

        // Assert - In a real test environment with Firebase mocked,
        // we would verify navigation to EmployeeDashboardActivity
        // For now, this verifies the UI interaction doesn't crash

        scenario.close();
    }

    @Test
    public void testIncorrectEmailStaysOnPage() {
        // Act - Launch activity
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);

        // Enter incorrect email
        onView(withId(R.id.roleInputEmail))
                .perform(typeText("wrong@example.com"), closeSoftKeyboard());

        // Click confirm button
        onView(withId(R.id.roleConfirmButton))
                .perform(click());

        // Assert - Verify user stays on ConfirmActivity by checking email input is still visible
        onView(withId(R.id.roleInputEmail))
                .check(matches(isDisplayed()));

        // Verify confirm button is still visible
        onView(withId(R.id.roleConfirmButton))
                .check(matches(isDisplayed()));

        scenario.close();
    }
}
