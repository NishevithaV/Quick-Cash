package com.example.quick_cash.views.registration;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.core.app.ActivityScenario;

import com.example.quick_cash.R;
import com.example.quick_cash.views.registration.RegistrationActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * The type Espresso test.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class EspressoTest {

    private Context context;
    private ActivityScenario<RegistrationActivity> scenario;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
        scenario = ActivityScenario.launch(RegistrationActivity.class);
    }

    /**
     * Check if valid user.
     */
    @Test
    public void checkIfValidUser() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Roy Doe"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("john@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("StrongPass!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        // small delay for toast to trigger
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.REGISTRATION_SUCCESSFUL),
                RegistrationActivity.lastToastMessage
        );

    }

    /**
     * Check if name is valid.
     */
    @Test
    public void checkIfNameIsValid() {
        onView(withId(R.id.employer_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark SHark"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.REGISTRATION_SUCCESSFUL),
                RegistrationActivity.lastToastMessage
        );
    }

    /**
     * Check if name is empty.
     */
    @Test
    public void checkIfNameIsEmpty() {
        onView(withId(R.id.employer_button)).perform(click());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.INVALID_NAME),
                RegistrationActivity.lastToastMessage
        );
    }

    /**
     * Check if email is invalid.
     */
    @Test
    public void checkIfEmailIsInvalid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Lawrence"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("usergmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.INVALID_EMAIL),
                RegistrationActivity.lastToastMessage
        );
    }

    /**
     * Check if email is empty.
     */
    @Test
    public void checkIfEmailIsEmpty() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.INVALID_EMAIL),
                RegistrationActivity.lastToastMessage
        );
    }

    /**
     * Check if email is valid.
     */
    @Test
    public void checkIfEmailIsValid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.REGISTRATION_SUCCESSFUL),
                RegistrationActivity.lastToastMessage
        );
    }

    /**
     * Check if password empty.
     */
    @Test
    public void checkIfPasswordEmpty() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("mark@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.EMPTY_PASSWORD),
                RegistrationActivity.lastToastMessage
        );
    }

    /**
     * Check if password is invalid.
     */
    @Test
    public void checkIfPasswordIsInvalid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.INVALID_PASSWORD),
                RegistrationActivity.lastToastMessage
        );
    }

    /**
     * Check if password is valid.
     */
    @Test
    public void checkIfPasswordIsValid() {
        onView(withId(R.id.employee_button)).perform(click());
        onView(withId(R.id.name_input)).perform(typeText("Mark"), closeSoftKeyboard());
        onView(withId(R.id.email_input)).perform(typeText("user@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.password_input)).perform(typeText("Password!123"), closeSoftKeyboard());
        onView(withId(R.id.register_button)).perform(click());

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertEquals(
                context.getString(R.string.REGISTRATION_SUCCESSFUL),
                RegistrationActivity.lastToastMessage
        );
    }
}
