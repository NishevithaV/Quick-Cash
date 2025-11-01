package com.example.quick_cash.reset_password;

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

public class UIAutomatorTest {
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quick_cash";
    private UiDevice device;

    private final String resetBtnID = "resetButtonID";
    private final String loginLink = "resetTologinLinkID";
    private final String emailInpID = "resetEmailInputID";
    private final String statusTxtID = "resetPsswdStatusTextID";

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();

        Intent launcherIntent = new Intent();
        launcherIntent.setClassName(
                launcherPackageName,
                launcherPackageName+".reset_password.ResetPasswordActivity"
        );
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfResetPasswordPageIsVisible() {
        UiObject2 resetBtn = device.findObject(By.res(launcherPackageName, resetBtnID));
        assertNotNull(resetBtn);
        UiObject2 emailBox = device.findObject(By.res(launcherPackageName, emailInpID));
        assertNotNull(emailBox);
    }

    @Test
    public void testResetButtonWithInvalidEmail() {
        device.findObject(By.res(launcherPackageName, emailInpID)).setText("bademail.com");
        device.findObject(By.res(launcherPackageName, resetBtnID)).click();
        device.waitForIdle();
        assertTrue(device.findObject(By.res(launcherPackageName, statusTxtID)).getText().contains("Enter enter a Valid Email"));
    }

    @Test
    public void testResetButtonWithValidEmail() throws InterruptedException {
        String testEmail = "test-user@bigmoney.com";
        device.findObject(By.res(launcherPackageName, emailInpID)).setText(testEmail);
        device.findObject(By.res(launcherPackageName, resetBtnID)).click();
        device.waitForIdle();
        device.wait(Until.hasObject(By.res(launcherPackageName, statusTxtID)), 5000);
        String status = device.findObject(By.res(launcherPackageName, statusTxtID)).getText();
        String login = device.findObject(By.res(launcherPackageName, loginLink)).getText();
        assertTrue(status.contains("email sent"));
        assertTrue(login.contains("Go To Login"));
    }

    @Test
    public void testGoToLoginAfterReset() {
        String testEmail = "test-user@bigmoney.com";
        device.findObject(By.res(launcherPackageName, emailInpID)).setText(testEmail);
        device.findObject(By.res(launcherPackageName, resetBtnID)).click();
        device.waitForIdle();
        device.wait(Until.hasObject(By.res(launcherPackageName, statusTxtID)), 5000);
        UiObject2 status = device.findObject(By.res(launcherPackageName, statusTxtID));
        UiObject2 loginBtn = device.findObject(By.res(launcherPackageName, loginLink));
        assertTrue(status.getText().contains("email sent"));
        assertTrue(loginBtn.getText().contains("Go To Login"));

        loginBtn.click();
        UiObject loginHeader = device.findObject(new UiSelector().textContains("Welcome Back"));
        loginHeader.exists();
        UiObject loginPageBtn = device.findObject(new UiSelector().textContains("Login"));
        loginPageBtn.exists();
    }
}
