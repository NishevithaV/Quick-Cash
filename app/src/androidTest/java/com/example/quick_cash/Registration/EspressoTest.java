package com.example.quick_cash.Registration;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

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
        onView(withId(R.id.name_input)).perform(typeText("John Doe"));
        onView(withId(R.id.email_input)).perform(typeText("john@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("StrongPass123"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText("Registration successful")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfInvalidUserType() {
        onView(withId(R.id.name_input)).perform(typeText("John"));
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("12345"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText("Please select a user type")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfNameIsValid() {
        onView(withId(R.id.employer_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"));
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("Password!123"));
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.statusMessage))
                .check(matches(withText("")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfNameIsEmpty() {
        onView(withId(R.id.employer_button)).perform(click());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("Password!123"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText("Name cannot be empty")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"));
        onView(withId(R.id.email_input)).perform(typeText("usergmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("Password!123"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText("Incorrect Email")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"));
        onView(withId(R.id.password_input)).perform(typeText("Password!123"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText("Need to provide Email")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfEmailIsValid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"));
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("Password!123"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText("")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordEmpty() {
        onView(withId(R.id.name_input)).perform(typeText("Mark"));
        onView(withId(R.id.email_input)).perform(typeText("mark@example.com"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage))
                .check(matches(withText("Password cannot be empty")))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"));
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("Password123"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage)).check(matches(withText("Password need to have one upper case, one number and one special character and be at least 8 characters"))).check(matches(isDisplayed()));
    }
    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"));
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"));
        onView(withId(R.id.password_input)).perform(typeText("Password!123"));
        onView(withId(R.id.register_button)).perform(click());

        onView(withId(R.id.statusMessage)).check(matches(withText(""))).check(matches(isDisplayed()));
    }

}

