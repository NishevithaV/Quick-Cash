package com.example.quick_cash.views.reset_password;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
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

/**
 * The type Espresso test.
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    /**
     * The Activity scenario.
     */
    public ActivityScenario<ResetPasswordActivity> activityScenario;
    /**
     * The Test exist email.
     */
    String testExistEmail = "iamjohn@johnny.com";
    /**
     * The Test valid email not exist.
     */
    String testValidEmailNotExist = "thisemaildoesnotexitindb@validemail.com";


    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        activityScenario = ActivityScenario.launch(ResetPasswordActivity.class);
        activityScenario.onActivity(activity -> {
        });
    }

    /**
     * Test valid email.
     */
    @Test
    public void testValidEmail() {
        onView(withId(R.id.resetEmailInputID)).perform(typeText(testValidEmailNotExist), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.resetPsswdStatusTextID)).check(matches(not(withText(R.string.INVALID_EMAIL))));
    }

    /**
     * Test invalid email.
     */
    @Test
    public void testInvalidEmail() {
        onView(withId(R.id.resetEmailInputID)).perform(typeText("bademail.com"), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.resetPsswdStatusTextID)).check(matches(withText(R.string.INVALID_EMAIL)));
    }

    /**
     * Test same password.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSamePassword() throws InterruptedException{
        onView(withId(R.id.resetEmailInputID)).perform(typeText(testExistEmail), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.currPasswd)).perform(typeText("12345678bB%"), closeSoftKeyboard());
        onView(withId(R.id.newPasswd)).perform(typeText("12345678bB%"), closeSoftKeyboard());
        onView(withId(R.id.formResetButtonID)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.formResetPsswdStatusTextID)).check(matches(withText("Failed to Reset Password: Same Password")));
        onView(withId(R.id.resetTologinLinkID)).check(matches(not(isDisplayed())));
    }

    /**
     * Test invalid password.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testInvalidPassword() throws InterruptedException {
        onView(withId(R.id.resetEmailInputID)).perform(typeText(testExistEmail), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.currPasswd)).perform(typeText("12345678bB%"), closeSoftKeyboard());
        onView(withId(R.id.newPasswd)).perform(typeText("badpassword"), closeSoftKeyboard());
        onView(withId(R.id.formResetButtonID)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.formResetPsswdStatusTextID)).check(matches(withText(R.string.INVALID_PASSWORD)));
        onView(withId(R.id.resetTologinLinkID)).check(matches(not(isDisplayed())));
    }

    /**
     * Test email not exist.
     */
    @Test
    public void testEmailNotExist() {
        onView(withId(R.id.resetEmailInputID)).perform(typeText(testValidEmailNotExist), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.resetPsswdStatusTextID)).check(matches(withText(R.string.EMAIL_NOT_EXIST)));
    }

    /**
     * Test email exists.
     */
    @Test
    public void testEmailExists() {
        onView(withId(R.id.resetEmailInputID)).perform(typeText(testExistEmail), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());
        onView(withId(R.id.formResetButtonID)).check(matches(isDisplayed()));
    }

    /**
     * Test successful reset send/Also tests valid password.
     *
     * @throws InterruptedException the interrupted exception
     */
    @Test
    public void testSuccessfulReset() throws InterruptedException {
        onView(withId(R.id.resetEmailInputID)).perform(typeText(testExistEmail), closeSoftKeyboard());
        onView(withId(R.id.resetButtonID)).perform(click());

        Thread.sleep(2000);

        onView(withId(R.id.currPasswd)).perform(typeText("12345678bB%"), closeSoftKeyboard());
        onView(withId(R.id.newPasswd)).perform(typeText("12345678aA%"), closeSoftKeyboard());
        onView(withId(R.id.formResetButtonID)).perform(click());

        Thread.sleep(2000);
        onView(withId(R.id.formResetPsswdStatusTextID)).check(matches(withText(R.string.RESET_SEND_SUCCESSFUL)));
        onView(withId(R.id.resetTologinLinkID)).check(matches(isDisplayed()));

        // clean up
        onView(withId(R.id.currPasswd)).perform(clearText(), typeText("12345678aA%"), closeSoftKeyboard());
        onView(withId(R.id.newPasswd)).perform(clearText(), typeText("12345678bB%"), closeSoftKeyboard());
        onView(withId(R.id.formResetButtonID)).perform(click());
    }
}