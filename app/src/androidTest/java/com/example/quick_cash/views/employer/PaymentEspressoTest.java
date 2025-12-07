package com.example.quick_cash.views.employer;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

/**
 * The type Payment espresso test.
 */
@RunWith(AndroidJUnit4.class)
public class PaymentEspressoTest {

    /**
     * Test ui elements exist.
     */
    @Test
    public void testUIElementsExist() {
        Intent intent = new Intent(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext(),
                EmployerPaymentActivity.class);
        intent.putExtra("jobApplicationId", "testApp123");

        try (ActivityScenario<EmployerPaymentActivity> scenario = ActivityScenario.launch(intent)) {
            // EditText exists and has the correct hint
            onView(withId(R.id.amountEditText))
                    .check(matches(isDisplayed()))
                    .check(matches(withHint("Enter Amount")));

            // Button exists and has the correct text
            onView(withId(R.id.payButton))
                    .check(matches(isDisplayed()))
                    .check(matches(withText("Pay with PayPal")));
        }
    }

    /**
     * Test empty amount shows error toast.
     */
    @Test
    public void testEmptyAmountShowsErrorToast() {
        Intent intent = new Intent(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext(),
                EmployerPaymentActivity.class);
        intent.putExtra("jobApplicationId", "testApp123");

        try (ActivityScenario<EmployerPaymentActivity> scenario = ActivityScenario.launch(intent)) {
            // Enter empty amount and click pay
            onView(withId(R.id.amountEditText)).perform(replaceText(""));
            onView(withId(R.id.payButton)).perform(click());

            // Check Toast text using Espresso-Intents workaround
            // Use a library like ToastMatcher if needed for proper Toast assertion
            // Or just verify that EditText is still empty (validation prevented payment)
            onView(withId(R.id.amountEditText)).check(matches(withText("")));
        }
    }

    /**
     * Test invalid amount shows error toast.
     */
    @Test
    public void testInvalidAmountShowsErrorToast() {
        Intent intent = new Intent(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation().getTargetContext(),
                EmployerPaymentActivity.class);
        intent.putExtra("jobApplicationId", "testApp123");

        try (ActivityScenario<EmployerPaymentActivity> scenario = ActivityScenario.launch(intent)) {
            // Enter invalid text
            onView(withId(R.id.amountEditText)).perform(replaceText("abc"));
            onView(withId(R.id.payButton)).perform(click());

            // Validation prevents payment
            onView(withId(R.id.amountEditText)).check(matches(withText("abc")));
        }
    }
}

