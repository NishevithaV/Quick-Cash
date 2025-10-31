package com.example.quick_cash.login;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * UI Automator test for Login → Dashboard navigation
 * Verifies AT-1 and AT-3 at the system-UI level.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginUiAutomatorTest {

    private static final String PACKAGE = "com.example.quick_cash";
    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());

        // Start from the home screen
        device.pressHome();

        // Launch the app
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = context.getPackageManager()
                .getLaunchIntentForPackage(PACKAGE)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        // Wait for app to appear
        device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testSuccessfulLoginRedirectsToDashboard() throws Exception {
        // Wait for email field
        device.wait(Until.hasObject(By.res(PACKAGE, "email_input")), 3000);

        // Enter credentials
        UiObject2 emailField = device.findObject(By.res(PACKAGE, "email_input"));
        UiObject2 passwordField = device.findObject(By.res(PACKAGE, "password_input"));
        UiObject2 loginButton = device.findObject(By.res(PACKAGE, "login_button"));

        assertNotNull("Email input not found", emailField);
        assertNotNull("Password input not found", passwordField);
        assertNotNull("Login button not found", loginButton);

        emailField.setText("amber@gmail.com");
        passwordField.setText("Sadashva@123");
        loginButton.click();

        // Wait for dashboard to appear (mock dashboard text)
        boolean employerDashboardLoaded =
                device.wait(Until.hasObject(By.textContains("Employer Dashboard Loaded Successfully")), 5000);
        boolean employeeDashboardLoaded =
                device.wait(Until.hasObject(By.textContains("Employee Dashboard Loaded Successfully")), 5000);

        assertTrue("Expected dashboard not displayed",
                employerDashboardLoaded || employeeDashboardLoaded);
    }

    @Test
    public void testSignUpRedirectOpensRegistrationPage() {
        // Tap "Sign up" link
        UiObject2 signupText = device.findObject(By.res(PACKAGE, "signup_redirect"));
        assertNotNull("Signup link not found", signupText);
        signupText.click();

        // Wait for registration page
        boolean registrationOpened =
                device.wait(Until.hasObject(By.textContains("Temporary Registration Page Loaded Successfully")), 5000);
        assertTrue("Registration page did not open", registrationOpened);
    }
}