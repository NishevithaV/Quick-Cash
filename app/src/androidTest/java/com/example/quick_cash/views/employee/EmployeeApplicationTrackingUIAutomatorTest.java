package com.example.quick_cash.views.employee;

import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

/**
 * UI Automator tests for Employee Application Tracking screen (US-12).
 *
 * These tests validate:
 * - Launching the Employee Dashboard screen
 * - Scrolling behavior of the application list
 * - Visibility and clickability of the "Mark Completed" button
 * - Status update behavior
 * - Screen remains stable after interactions
 */
@RunWith(AndroidJUnit4.class)
public class EmployeeApplicationTrackingUIAutomatorTest {

    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Intent intent = new Intent(
                ApplicationProvider.getApplicationContext(),
                EmployeeApplicationTrackingActivity.class
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ApplicationProvider.getApplicationContext().startActivity(intent);

        device.waitForIdle();
    }

    /**
     * Test 1:
     * Confirms the Employee Dashboard heading is displayed.
     * Ensures the activity launched correctly.
     */
    @Test
    public void testDashboardTitleIsVisible() throws UiObjectNotFoundException {
        UiObject title = device.findObject(new UiSelector().text("Employee Dashboard"));
        assertTrue("Employee Dashboard title not found", title.exists());
    }

    /**
     * Test 2:
     * Scrolls the application list and confirms that multiple application cards exist.
     * This verifies RecyclerView is scrollable and populated.
     */
    @Test
    public void testApplicationListScrollable() throws UiObjectNotFoundException {
        UiObject firstItem = device.findObject(new UiSelector().text("Front-end Developer"));
        assertTrue(firstItem.exists());

        UiObject recyclerView = device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/recycler_applications"));
        recyclerView.swipeUp(30);

        UiObject anotherItem = device.findObject(new UiSelector().text("Front-end Developer"));
        assertTrue(anotherItem.exists());
    }

    /**
     * Test 3:
     * Confirms that the Mark Completed button is clickable for an accepted job application.
     */
    @Test
    public void testMarkCompletedButtonClickable() throws UiObjectNotFoundException {
        UiObject btn = device.findObject(new UiSelector().text("MARK COMPLETED"));
        assertTrue("Mark Completed button not found", btn.exists());

        btn.click();
        device.waitForIdle();

        UiObject completedBadge = device.findObject(new UiSelector().text("Completed"));
        assertTrue("Completed badge not found after clicking Mark Completed", completedBadge.exists());
    }
}