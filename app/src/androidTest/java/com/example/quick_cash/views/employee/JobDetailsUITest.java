package com.example.quick_cash.views.employee;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * The type Job details ui test.
 */
@RunWith(AndroidJUnit4.class)
public class JobDetailsUITest {

    private UiDevice device;

    /**
     * Sets .
     */
    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        Context ctx = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(ctx, JobDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("jobID", "123");
        intent.putExtra("title", "Test Title");
        intent.putExtra("employer", "Test Employer");
        intent.putExtra("category", "Finance");
        intent.putExtra("description", "Test description");
        ctx.startActivity(intent);
    }

    /**
     * Test all views visible.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAllViewsVisible() throws Exception {
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobTitle")).exists());
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobEmployer")).exists());
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobCategory")).exists());
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobDesc")).exists());
    }

    /**
     * Test apply button opens submission.
     */
    @Test
    public void testApplyButtonOpensSubmission() {
        UiObject2 applyButton = device.wait(Until.findObject(By.res("com.example.quick_cash", "applyButton")), 5000);
        applyButton.click();
        UiObject2 heading = device.wait(Until.findObject(By.res("com.example.quick_cash", "cvrLtrHead")), 5000);
        assertNotNull(heading);
    }
}
