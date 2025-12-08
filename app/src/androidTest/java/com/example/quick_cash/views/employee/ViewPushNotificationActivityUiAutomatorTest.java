package com.example.quick_cash.views.employee;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;

import android.content.Intent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * The type View push notification activity ui automator test.
 */
@RunWith(AndroidJUnit4.class)
public class ViewPushNotificationActivityUiAutomatorTest {

    private UiDevice device;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
    }

    /**
     * Check injected data displayed.
     *
     * @throws UiObjectNotFoundException the ui object not found exception
     */
    @Test
    public void checkInjectedDataDisplayed() throws UiObjectNotFoundException {
        Intent intent = new Intent();
        intent.putExtra("title", "Test Job Title");
        intent.putExtra("body", "This is a test body");
        intent.putExtra("job_id", "12345");
        intent.putExtra("jobLocation", "Halifax, NS");
        intent.setClassName("com.example.quick_cash",
                "com.example.quick_cash.views.employee.ViewPushNotificationActivity");

        // Launch the activity
        androidx.test.core.app.ActivityScenario<ViewPushNotificationActivity> scenario =
                androidx.test.core.app.ActivityScenario.launch(intent);


        // Verify text in UIAutomator
        assertEquals("Test Job Title", device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/titleTV"))
                .getText());
        assertEquals("This is a test body", device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/bodyTV"))
                .getText());
        assertEquals("Halifax, NS", device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobLocationTV"))
                .getText());
        assertEquals("Test Job Title", device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobIdTV"))
                .getText());
    }
}
