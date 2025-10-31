package com.example.quick_cash.reset_password;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.idling.CountingIdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    public ActivityScenario<ResetPasswordActivity> activityScenario;

    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(ResetPasswordActivity.class);
        activityScenario.onActivity(activity -> {
        });
    }

    @Test
    public void testValidEmail() {
        onView(withId(R.id.emailInputID)).perform(typeText("thisemaildoesnotexitindb@validemail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.statusTextID)).check(matches(not(withText(R.string.INVALID_EMAIL))));
    }

    @Test
    public void testInvalidEmail() {
        onView(withId(R.id.emailInputID)).perform(typeText("bademail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.statusTextID)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    @Test
    public void testSuccessfulResetSend() {

        onView(withId(R.id.emailInputID)).perform(typeText("test-user@bigmoney.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.statusTextID)).check(matches(withText(R.string.RESET_SEND_SUCCESSFUL)));
    }
}