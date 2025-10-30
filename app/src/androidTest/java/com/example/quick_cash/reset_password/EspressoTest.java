package com.example.quick_cash.reset_password;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    @Rule
    public ActivityScenarioRule<ResetPasswordActivity> rule =
            new ActivityScenarioRule<>(ResetPasswordActivity.class);

    @Test
    public void testSuccesfulToast() {
        onView(withId(R.id.emailInputID)).perform(typeText("alex@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withText("reset email sent!")).check(matches(withText("reset email sent!")));
    }

    @Test
    public void testErrorToast() {
        onView(withId(R.id.emailInputID)).perform(typeText("alex.gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withText("invalid email")).check(matches(withText("invalid email")));
    }
}