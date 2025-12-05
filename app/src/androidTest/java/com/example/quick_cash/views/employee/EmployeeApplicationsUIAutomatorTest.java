package com.example.quick_cash.views.employee;

import static org.junit.Assert.assertFalse;
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

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class EmployeeApplicationsUIAutomatorTest {
    private UiDevice device;
    final String launcherPackageName = "com.example.quick_cash";
    private static final int LAUNCH_TIMEOUT = 5000;


    @Before
    public void setup() {
        FirebaseAuth.getInstance().signOut();
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = new Intent();
        launcherIntent.setClassName(
                launcherPackageName,
                launcherPackageName+".views.employee.EmployeeDashboardActivity"
        );
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Assert.assertNotNull(launcherIntent);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testNavigation_AllUiElementsVisible() throws Exception {
        UiObject2 myAppsButton = device.wait(Until.findObject(By.res(launcherPackageName, "btnMyApps")), 2000);
        assertNotNull(myAppsButton);
        myAppsButton.click();

        device.wait(Until.findObject(By.res(launcherPackageName, "tv_employee_dashboard_title")), 3000);

        UiObject header = device.findObject(new UiSelector().textContains("My Applications"));
        assertTrue(header.exists());

        UiObject2 resHead = device.findObject(By.res(launcherPackageName+":id/"+"recycler_applications"));
        assertNotNull(resHead);
    }

}
