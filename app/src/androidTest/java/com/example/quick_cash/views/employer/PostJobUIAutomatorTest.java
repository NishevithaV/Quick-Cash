package com.example.quick_cash.views.employer;

import static org.junit.Assert.assertNotNull;
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

import com.google.firebase.messaging.FirebaseMessaging;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

/**
 * The type Post job ui automator test.
 */
@RunWith(AndroidJUnit4.class)
public class PostJobUIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    private static final int REDIRECT_TIMEOUT = 6000;
    /**
     * The Launcher package name.
     */
    final String launcherPackageName = "com.example.quick_cash";
    private UiDevice device;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        FirebaseMessaging.getInstance().subscribeToTopic("jobs");
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = new Intent();
        launcherIntent.putExtra("isTest", true);
        launcherIntent.setClassName(
                launcherPackageName,
                launcherPackageName+".views.employer.PostFormActivity"
        );
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Assert.assertNotNull(launcherIntent);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Check if post form is visible.
     */
    @Test
    public void checkIfPostFormIsVisible() {
        UiObject jobTitleField = device.findObject(new UiSelector().text("Job Title"));
        assertTrue(jobTitleField.exists());
        UiObject jobCategorySpinner = device.findObject(new UiSelector().textContains("Select job category"));
        jobCategorySpinner.exists();
        UiObject locationField = device.findObject(new UiSelector().textContains("Job Location"));
        assertTrue(locationField.exists());
        UiObject applicationDeadlineField = device.findObject(new UiSelector().text("Application Deadline (YYYY-MM-DD)"));
        assertTrue(applicationDeadlineField.exists());
        UiObject descriptionField = device.findObject(new UiSelector().text("Job Description"));
        assertTrue(descriptionField.exists());
        UiObject postJobButton = device.findObject(new UiSelector().text("Post Job"));
        assertTrue(postJobButton.exists());
    }

    /**
     * Check if job posted successfully.
     *
     * @throws UiObjectNotFoundException the ui object not found exception
     */
    @Test
    public void checkIfJobPostedSuccessfully() throws UiObjectNotFoundException {
        UiObject jobTitleField = device.findObject(new UiSelector().text("Job Title"));
        jobTitleField.setText("Backend Developer");
        UiObject jobCategorySpinner = device.findObject(new UiSelector().textContains("Select job category"));
        jobCategorySpinner.click();
        List<UiObject2> types = device.findObjects(By.res("android:id/text1"));
        types.get(4).click();
        UiObject locationField = device.findObject(new UiSelector().textContains("Job Location"));
        locationField.setText("Halifax, NS");
        UiObject applicationDeadlineField = device.findObject(new UiSelector().text("Application Deadline (YYYY-MM-DD)"));
        applicationDeadlineField.setText("2025-12-21");
        UiObject descriptionField = device.findObject(new UiSelector().text("Job Description"));
        descriptionField.setText("Sample description");
        UiObject postJobButton = device.findObject(new UiSelector().text("Post Job"));
        postJobButton.click();
        device.wait(Until.hasObject(By.text("Job posted successfully")), REDIRECT_TIMEOUT);
        UiObject result = device.findObject(new UiSelector().text("Job posted successfully"));
        assertNotNull("Failed to post job!", result);
    }


    /**
     * Check if redirected after post.
     *
     * @throws UiObjectNotFoundException the ui object not found exception
     */
    @Test
    public void checkIfRedirectedAfterPost() throws UiObjectNotFoundException {
        UiObject jobTitleField = device.findObject(new UiSelector().text("Job Title"));
        jobTitleField.setText("Backend Developer");
        UiObject jobCategorySpinner = device.findObject(new UiSelector().textContains("Select job category"));
        jobCategorySpinner.click();
        List<UiObject2> types = device.findObjects(By.res("android:id/text1"));
        types.get(4).click();
        UiObject locationField = device.findObject(new UiSelector().textContains("Job Location"));
        locationField.setText("Halifax, NS");
        UiObject applicationDeadlineField = device.findObject(new UiSelector().text("Application Deadline (YYYY-MM-DD)"));
        applicationDeadlineField.setText("2025-12-21");
        UiObject descriptionField = device.findObject(new UiSelector().text("Job Description"));
        descriptionField.setText("Sample description");
        UiObject postJobButton = device.findObject(new UiSelector().text("Post Job"));
        postJobButton.click();
        device.wait(Until.hasObject(By.text("Your Posted Jobs")), REDIRECT_TIMEOUT);
        UiObject2 dashboardTitle = device.findObject(By.text("Your Posted Jobs"));
        assertNotNull("Failed to redirect to Employer Dashboard!", dashboardTitle);
    }


    /**
     * Test notification appears.
     *
     * @throws Exception the exception
     */
    @Test
    public void testNotificationAppears() throws Exception {
        UiObject jobTitleField = device.findObject(new UiSelector().text("Job Title"));
        jobTitleField.setText("Backend Developer");
        UiObject jobCategorySpinner = device.findObject(new UiSelector().textContains("Select job category"));
        jobCategorySpinner.click();
        List<UiObject2> types = device.findObjects(By.res("android:id/text1"));
        types.get(4).click();
        UiObject locationField = device.findObject(new UiSelector().textContains("Job Location"));
        locationField.setText("Halifax, NS");
        UiObject applicationDeadlineField = device.findObject(new UiSelector().text("Application Deadline (YYYY-MM-DD)"));
        applicationDeadlineField.setText("2025-12-21");
        UiObject descriptionField = device.findObject(new UiSelector().text("Job Description"));
        descriptionField.setText("Sample description");
        UiObject postJobButton = device.findObject(new UiSelector().text("Post Job"));
        postJobButton.click();

        device.wait(Until.hasObject(By.text("Notification Sent Successfully")), REDIRECT_TIMEOUT);
        UiObject result = device.findObject(new UiSelector().text("Notification Sent Successfully"));
        assertNotNull("Failed to send notification!", result);
    }
}

