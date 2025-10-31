package com.example.quick_cash.registration;


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
public class UIAutomator {

    private static final int LAUNCH_TIMEOUT = 5000;
    private UiDevice device;

    @Before
    public void setUp() {
        device = UiDevice.getInstance(androidx.test.platform.app.InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = context.getPackageManager().getLaunchIntentForPackage("com.example.quick_cash");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg("com.example.quick_cash").depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testPageItemsVisible() {
        UiObject2 employeeBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "employee_button")), 3000);
        UiObject2 employerBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "employer_button")), 3000);
        UiObject2 registerBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "register_button")), 3000);
        UiObject2 loginBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "login_button")), 3000);

        assertNotNull("Employee button not visible", employeeBtn);
        assertNotNull("Employer button not visible", employerBtn);
        assertNotNull("Register button not visible", registerBtn);
        assertNotNull("Login button not visible", loginBtn);
    }

    @Test
    public void testEmployeeButtonWorks() {
        UiObject2 employeeBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "employee_button")), 3000);
        assertNotNull("Employee button not found", employeeBtn);
        employeeBtn.click();
    }

    @Test
    public void testEmployerButtonWorks() {
        UiObject2 employerBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "employer_button")), 3000);
        assertNotNull("Employer button not found", employerBtn);
        employerBtn.click();
    }

    @Test
    public void testRegisterButtonWorks() {
        UiObject2 registerBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "register_button")), 3000);
        assertNotNull("Register button not found", registerBtn);
        registerBtn.click();
    }

    @Test
    public void testLoginButtonWorks() {
        UiObject2 loginBtn = device.wait(Until.findObject(By.res("com.example.quick_cash", "login_button")), 3000);
        assertNotNull("Login button not found", loginBtn);
        loginBtn.click();
    }
}
