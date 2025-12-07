package com.example.quick_cash.views.employee;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiSelector;

import com.example.quick_cash.views.employee.SubmitApplicationActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * The type Submit application ui test.
 */
@RunWith(AndroidJUnit4.class)
public class SubmitApplicationUITest {

    private UiDevice device;

    /**
     * Sets .
     */
    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context ctx = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(ctx, SubmitApplicationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("jobID", "123");
        ctx.startActivity(intent);
    }

    /**
     * Test all views visible.
     *
     * @throws Exception the exception
     */
    @Test
    public void testAllViewsVisible() throws Exception {
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/cvrLtrInput")).exists());
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/submitLtrBtn")).exists());
    }
}
