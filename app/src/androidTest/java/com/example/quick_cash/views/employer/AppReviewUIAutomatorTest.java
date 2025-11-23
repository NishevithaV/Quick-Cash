package com.example.quick_cash.views.employer;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;

import org.junit.Before;
import org.junit.Test;
public class AppReviewUIAutomatorTest {
    private UiDevice device;
    final String launcherPackageName = "com.example.quick_cash";


    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();

        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(launcherPackageName+".views.employer.ApplicationReviewActivity");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        device.waitForIdle();
    }

    @Test
    public void testAllUiElementsVisible() throws Exception {

        // Heading
        UiObject heading = device.findObject(
                new UiSelector().resourceId(launcherPackageName+":id/viewAppsHeading"));
        assertTrue(heading.exists());

        // Spinner
        UiObject spinner = device.findObject(
                new UiSelector().resourceId(launcherPackageName+":id/statusFilter"));
        assertTrue(spinner.exists());

        // ListView
        UiObject listView = device.findObject(new UiSelector().resourceId(launcherPackageName+":id/appsResultsView"));
        assertTrue(listView.exists());
    }
}
