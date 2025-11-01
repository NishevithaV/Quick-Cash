package com.example.quick_cash.reset_password;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.not;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    public ActivityScenario<ResetPasswordActivity> activityScenario;
    String testEmail = "emiyusuffe@gmail.com";

    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(ResetPasswordActivity.class);
        activityScenario.onActivity(activity -> {
        });
    }

    @Test
    public void testValidEmail() {
        onView(withId(R.id.resetEmailInputID)).perform(typeText("thisemaildoesnotexitindb@validemail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.resetPsswdStatusTextID)).check(matches(not(withText(R.string.INVALID_EMAIL))));
    }

    @Test
    public void testInvalidEmail() {
        onView(withId(R.id.resetEmailInputID)).perform(typeText("bademail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.resetPsswdStatusTextID)).check(matches(withText(R.string.INVALID_EMAIL)));
        onView(withId(R.id.resetTologinLinkID)).check(matches(not(isDisplayed())));
    }

    @Test
    public void testSuccessfulResetSend() {
        onView(withId(R.id.resetEmailInputID)).perform(typeText(testEmail), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }
        onView(withId(R.id.resetPsswdStatusTextID)).check(matches(withText(R.string.RESET_SEND_SUCCESSFUL)));
        onView(withId(R.id.resetTologinLinkID)).check(matches(isDisplayed()));
    }
}