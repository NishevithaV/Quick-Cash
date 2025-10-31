package com.example.quick_cash.reset_password;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UIAutomatorTest {
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quick_cash";
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
    public void checkIfResetPasswordPageIsVisible() {
        UiObject2 resetBtn = device.findObject(By.res(launcherPackageName, "resetButtonID"));
        assertNotNull(resetBtn);
        UiObject2 emailBox = device.findObject(By.res(launcherPackageName, "emailInputID"));
        assertNotNull(emailBox);
    }

    @Test
    public void testResetButtonWithInvalidEmail() {
        device.findObject(By.res(launcherPackageName, "emailInputID")).setText("bademail.com");
        device.findObject(By.res(launcherPackageName, "resetButtonID")).click();
        device.waitForIdle();
        assertTrue(device.findObject(By.res(launcherPackageName, "statusTextID")).getText().contains("Enter enter a Valid Email"));
    }

    @Test
    public void testResetButtonWithValidEmail() throws InterruptedException {
        String testEmail = "emiyusuffe@gmail.com";
        device.findObject(By.res(launcherPackageName, "emailInputID")).setText(testEmail);
        device.findObject(By.res(launcherPackageName, "resetButtonID")).click();
        device.waitForIdle();
        device.wait(Until.hasObject(By.res(launcherPackageName, "statusTextID")), 5000);
        String status = device.findObject(By.res(launcherPackageName, "statusTextID")).getText();
        assertTrue(status.contains("email sent"));
    }
}
