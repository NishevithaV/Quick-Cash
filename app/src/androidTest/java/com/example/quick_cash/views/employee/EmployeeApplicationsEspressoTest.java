package com.example.quick_cash.views.employee;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.Matchers.allOf;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.IdlingPolicies;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;
import com.google.firebase.auth.FirebaseAuth;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EmployeeApplicationsEspressoTest {

    /**
     * The Activity scenario.
     */
    public ActivityScenario<EmployeeApplicationsActivity> activityScenario;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), EmployeeApplicationsActivity.class);
        intent.putExtra("isTest", true);
        activityScenario = ActivityScenario.launch(intent);
    }

    /**
     * Helper function to pause and wait for an amount of time
     *
     * @param millis time to wait for
     * @return ViewAction
     */
    public static ViewAction waitFor(long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds";
            }

            @Override
            public void perform(UiController uiController, View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        };
    }

    /**
     * Test applications load.
     */
    @Test
    public void testApplicationsLoad() {
        ArrayList<Application> testApps = new ArrayList<>();
        testApps.clear();
        testApps.add(new Application("testUserID", "cover letter", "pending", "testJobID"));
        activityScenario.onActivity(activity -> {
           activity.displayAppsForTest(testApps);
        });

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.recycler_applications)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.recycler_applications)).atPosition(0)
                .onChildView(withId(R.id.tv_job_title)).check(matches(withText("Test Job Title")));

    }

    /**
     * Test that applications can only be marked completed if they are accepted.
     */
    @Test
    public void testMarkCompletedPossibleOnlyIfAccepted() {
        ArrayList<Application> testApps = new ArrayList<>();
        testApps.clear();
        testApps.add(new Application("testUserID", "cover letter", "accepted", "testJobID"));
        testApps.add(new Application("testUserID", "cover letter", "pending", "testJobID"));
        activityScenario.onActivity(activity -> {
            activity.displayAppsForTest(testApps);
        });

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.recycler_applications)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.recycler_applications)).atPosition(0)
                .onChildView(withId(R.id.btn_mark_completed)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.recycler_applications)).atPosition(1)
                .onChildView(withId(R.id.btn_mark_completed)).check(matches(not(isDisplayed())));

    }

    /**
     * Test update application status to completed.
     */
    @Test
    public void testMarkCompleted() {
        ArrayList<Application> testApps = new ArrayList<>();
        testApps.clear();
        testApps.add(new Application("testUserID", "cover letter", "accepted", "testJobID"));
        activityScenario.onActivity(activity -> {
            activity.displayAppsForTest(testApps);
        });

        onView(isRoot()).perform(waitFor(1000));
        onView(withId(R.id.recycler_applications)).check(matches(isDisplayed()));
        onData(anything()).inAdapterView(withId(R.id.recycler_applications)).atPosition(0)
                .onChildView(withId(R.id.btn_mark_completed)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.recycler_applications)).atPosition(0)
                .onChildView(withId(R.id.tv_application_status)).check(matches(withText("completed")));
        onData(anything()).inAdapterView(withId(R.id.recycler_applications)).atPosition(0)
                .onChildView(withId(R.id.btn_mark_completed)).check(matches(not(isDisplayed())));
    }

}