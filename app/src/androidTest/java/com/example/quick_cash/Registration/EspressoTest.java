package com.example.quick_cash.Registration;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    private ActivityScenario<RegistrationActivity> activityScenario;

    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(RegistrationActivity.class);
    }

    @Test
    public void checkIfValidUser() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("John Doe"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("john@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("StrongPass!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.REGISTRATION_SUCCESSFUL)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfInvalidUserType() {
        onView(withId(R.id.name_input)).perform(typeText("JohnCiena"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password@123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.INVALID_USER_TYPE)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfNameIsValid() {
        onView(withId(R.id.employer_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark SHark"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.REGISTRATION_SUCCESSFUL)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfNameIsEmpty() {
        onView(withId(R.id.employer_button)).perform(click());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.INVALID_NAME)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Lawrence"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("usergmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.INVALID_EMAIL)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Nish"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.INVALID_EMAIL)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsValid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Tom"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.REGISTRATION_SUCCESSFUL)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordEmpty() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Akashaa"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("mark@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.EMPTY_PASSWORD)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Roy Stanley"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.INVALID_PASSWORD)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Micheal Scott"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText(R.string.REGISTRATION_SUCCESSFUL)))
                .check(matches(isDisplayed()));
    }
}
