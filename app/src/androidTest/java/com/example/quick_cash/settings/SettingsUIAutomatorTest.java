package com.example.quick_cash.settings;

import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

public class SettingsUIAutomatorTest {

    private static final String PACKAGE_NAME = "com.example.quick_cash";
    private static final int LAUNCH_TIMEOUT = 5000;
    
    private UiDevice device;

    @Before
    public void startSettingsActivity() {
        // Initialize UiDevice instance
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);    // Clear out any previous instances
        context.startActivity(intent);

        // Wait for the app to appear
        device.wait(Until.hasObject(By.pkg(PACKAGE_NAME).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testSettingsNavigation() {
        
    }

    @Test
    public void testSwitchRoleButtonExists() {
        UiObject2 switchRoleButton = device.findObject(By.text("Switch Role"));
        assertNotNull(switchRoleButton);
    }
}