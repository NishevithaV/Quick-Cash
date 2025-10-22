package com.example.quick_cash.job_search;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quick_cash.job_search";
    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackageName);
        Assert.assertNotNull(launcherIntent);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testElementsVisible() {
        UiObject searchBar = device.findObject(new UiSelector().textContains("Search for Jobs"));
        searchBar.exists();
        UiObject catSelect = device.findObject(new UiSelector().text("Category"));
        assertTrue(catSelect.exists());
        UiObject typeSelect = device.findObject(new UiSelector().text("Type"));
        assertTrue(typeSelect.exists());
        UiObject resultsHead = device.findObject(new UiSelector().text("Results"));
        assertTrue(resultsHead.exists());
        UiObject searchBtn = device.findObject(new UiSelector().text("Search"));
        assertTrue(searchBtn.exists());
    }

}
