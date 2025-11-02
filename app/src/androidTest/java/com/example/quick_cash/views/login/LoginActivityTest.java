package com.example.quick_cash.views.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.quick_cash.R;
import com.example.quick_cash.views.login.LoginActivity;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Espresso UI tests for LoginActivity.
 * Covers AT-1 (redirection), AT-2 (error handling), AT-3 (toast feedback).
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Set up before running tests
     */
    @Before
    public void setUp() {
        activityRule.getScenario();  // Automatically launches LoginActivity
    }

    /**
     * AT-2: Invalid email should show error box
     */
    @Test
    public void testInvalidEmailShowsErrorBox() {
        onView(withId(R.id.email_input))
                .perform(typeText("invalidEmail"), closeSoftKeyboard());
        onView(withId(R.id.password_input))
                .perform(typeText("abc123"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.error_box)).check(matches(isDisplayed()));
        onView(withId(R.id.error_title)).check(matches(withText("Invalid Email")));
    }

    /**
     * AT-2: Invalid password should show error box
     */
    @Test
    public void testInvalidPasswordShowsErrorBox() {
        onView(withId(R.id.email_input))
                .perform(typeText("user@example.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input))
                .perform(typeText("123"), closeSoftKeyboard());
        onView(withId(R.id.login_button)).perform(click());

        onView(withId(R.id.error_box)).check(matches(isDisplayed()));
        onView(withId(R.id.error_title)).check(matches(withText("Invalid Password")));
    }

    /**
     * AT-1: Sign-up redirect should open registration screen
     */
    @Test
    public void testSignupRedirectOpensRegistrationPage() {
        onView(withId(R.id.signup_redirect)).perform(click());
        onView(withText(CoreMatchers.containsString(
                "Register")))
                .check(matches(isDisplayed()));
    }

    // Helper to get current activity decor view for toast matching
    private android.view.View getCurrentActivityDecor() {
        final android.view.View[] decor = new android.view.View[1];
        activityRule.getScenario().onActivity(activity ->
                decor[0] = activity.getWindow().getDecorView());
        return decor[0];
    }
}