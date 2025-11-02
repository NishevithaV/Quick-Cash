package com.example.quick_cash.views.registration;


import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;

    private static final String PACKAGE = "com.example.quick_cash";

    @Before
    public void setUp() {
        device = UiDevice.getInstance(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(PACKAGE);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(PACKAGE).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testPageItemsVisible() {
        UiObject2 employeeBtn = device.wait(Until.findObject(By.res(PACKAGE, "employee_button")), 3000);
        UiObject2 employerBtn = device.wait(Until.findObject(By.res(PACKAGE, "employer_button")), 3000);
        UiObject2 registerBtn = device.wait(Until.findObject(By.res(PACKAGE, "register_button")), 3000);
        UiObject2 loginBtn = device.wait(Until.findObject(By.res(PACKAGE, "login_button")), 3000);

        assertNotNull("Employee button not visible", employeeBtn);
        assertNotNull("Employer button not visible", employerBtn);
        assertNotNull("Register button not visible", registerBtn);
        assertNotNull("Login button not visible", loginBtn);
    }

    @Test
    public void testEmployeeButtonWorks() {
        UiObject2 employeeBtn = device.wait(Until.findObject(By.res(PACKAGE, "employee_button")), 3000);
        assertNotNull("Employee button not found", employeeBtn);
        employeeBtn.click();
    }

    @Test
    public void testEmployerButtonWorks() {
        UiObject2 employerBtn = device.wait(Until.findObject(By.res(PACKAGE, "employer_button")), 3000);
        assertNotNull("Employer button not found", employerBtn);
        employerBtn.click();
    }

    @Test
    public void testRegisterButtonWorks() {
        UiObject2 registerBtn = device.wait(Until.findObject(By.res(PACKAGE, "register_button")), 3000);
        assertNotNull("Register button not found", registerBtn);
        registerBtn.click();
    }

    @Test
    public void testLoginButtonWorks() {
        UiObject2 loginBtn = device.wait(Until.findObject(By.res(PACKAGE, "login_button")), 3000);
        assertNotNull("Login button not found", loginBtn);
        loginBtn.click();
    }
}
