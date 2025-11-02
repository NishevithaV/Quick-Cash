package com.example.quick_cash.utils;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LogoutUIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quick_cash";
    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();

        // Explicit intent to start SettingsActivity directly
        Intent intent = new Intent();
        intent.setClassName(
                launcherPackageName,
                launcherPackageName+".views.settings.SettingsActivity"
        );
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfSettingsPageIsVisible() {
        UiObject LogoutButton = device.findObject(new UiSelector().text("Logout"));
        assertTrue(LogoutButton.exists());
        UiObject textView = device.findObject(new UiSelector().textContains("Settings"));
        assertTrue(textView.exists());
    }

    @Test
    public void checkIfCancelWorks() throws UiObjectNotFoundException {
        UiObject LogoutButton = device.findObject(new UiSelector().text("Logout"));
        LogoutButton.click();
        UiObject CancelButton = device.findObject(new UiSelector().text("Cancel"));
        CancelButton.click();

        UiObject textView = device.findObject(new UiSelector().textContains("Settings"));
        assertTrue(textView.exists());
        assertTrue(LogoutButton.exists());
    }

    @Test
    public void checkIfMoved2LoginPage() throws UiObjectNotFoundException{
        UiObject LogoutButton = device.findObject(new UiSelector().text("Logout"));
        LogoutButton.click();
        UiObject ConfirmButton = device.findObject(new UiSelector().text("Yes"));
        ConfirmButton.click();

        UiObject LoginLabel = device.findObject(new UiSelector().textContains("Login"));
        assertTrue(LoginLabel.exists());
    }
}
