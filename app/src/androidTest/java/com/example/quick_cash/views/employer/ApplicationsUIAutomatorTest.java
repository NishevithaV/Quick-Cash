package com.example.quick_cash.views.employer;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
public class ApplicationsUIAutomatorTest {
    private UiDevice device;
    final String launcherPackageName = "com.example.quick_cash";
    private static final int LAUNCH_TIMEOUT = 5000;


    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = new Intent();
        launcherIntent.setClassName(
                launcherPackageName,
                launcherPackageName+".views.employer.ApplicationsActivity"
        );
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Assert.assertNotNull(launcherIntent);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testAllUiElementsVisible() throws Exception {
        UiObject header = device.findObject(new UiSelector().textContains("Applications"));
        assertTrue(header.exists());

        UiObject2 heading = device.findObject(By.res(launcherPackageName+":id/"+"viewAppsHeading"));
        assertNotNull(heading);

        UiObject2 spinner = device.findObject(By.res(launcherPackageName+":id/"+"statusFilter"));
        assertNotNull(spinner);

        UiObject2 resHead = device.findObject(By.res(launcherPackageName+":id/"+"appsResHead"));
        assertNotNull(resHead);
    }

    /**
     * Test app click opens detail.
     */
    @Test
    public void testAppClickOpensDetail() {
        UiObject2 list = device.wait(Until.findObject(By.res(launcherPackageName, "appsResultsView")), 2000);
        assertNotNull(list);

        UiObject2 firstItem = list.getChildren().get(0);
        firstItem.click();

        device.wait(Until.findObject(By.textContains("Accept")), 3000);
        UiObject acceptBtn = device.findObject(new UiSelector().text("Accept"));
        assertTrue(acceptBtn.exists());
    }
}
