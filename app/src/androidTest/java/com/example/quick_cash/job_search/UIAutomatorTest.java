package com.example.quick_cash.job_search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
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

import java.util.List;

public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quick_cash";
    private UiDevice device;
    private final String gibberish = "aspfiojhnapsfanhaivhiuhawejj;aijhnhuiawoiefh";

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = new Intent();
        launcherIntent.setClassName(
                launcherPackageName,
                launcherPackageName+".job_search.JobSearchActivity"
        );
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Assert.assertNotNull(launcherIntent);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testElementsVisible() {
        UiObject header = device.findObject(new UiSelector().textContains("Find Jobs"));
        assertTrue(header.exists());
        UiObject2 userSearch = device.findObject(By.res(launcherPackageName+":id/"+"userSearch"));
        assertNotNull(userSearch);
        UiObject2 catSelect = device.findObject(By.res(launcherPackageName+":id/"+"catSelect"));
        assertNotNull(catSelect);
        UiObject searchBtn = device.findObject(new UiSelector().text("Go"));
        assertTrue(searchBtn.exists());
        UiObject resultsHeader = device.findObject(new UiSelector().text("Results"));
        assertTrue(resultsHeader.exists());
        UiObject2 resultsView = device.findObject(By.res(launcherPackageName+":id/"+"resultsView"));
        assertNotNull(resultsView);
    }

    @Test
    public void testSearchShowsMatchingResults() {
        UiObject2 input = device.findObject(By.res(launcherPackageName, "userSearch"));
        UiObject2 catSelect = device.findObject(By.res(launcherPackageName, "catSelect"));
        UiObject2 button = device.findObject(By.res(launcherPackageName, "searchBtn"));

        input.setText("Dev");
        catSelect.click();
        device.wait(Until.hasObject(By.text("Category")), 2000);
        UiObject2 category = device.findObject(By.text("Category"));
        category.click();
        button.click();
        device.wait(Until.findObject(By.text("Results")), 2000);
        UiObject resultsHeader = device.findObject(new UiSelector().text("Results"));
        assertTrue(resultsHeader.exists());
    }

    @Test
    public void testNoResultsMessageShown() {
        UiObject2 input = device.findObject(By.res(launcherPackageName, "userSearch"));
        UiObject2 button = device.findObject(By.res(launcherPackageName, "searchBtn"));

        input.setText(gibberish);
        button.click();

        device.wait(Until.findObject(By.text("No Results. Try a different search")), 2000);
        UiObject noResultsHeader = device.findObject(new UiSelector().text("No Results. Try a different search"));
        assertTrue(noResultsHeader.exists());
    }

    @Test
    public void testJobClickOpensDetail() {
        UiObject2 list = device.wait(Until.findObject(By.res(launcherPackageName, "resultsView")), 2000);
        assertNotNull(list);

        UiObject2 firstItem = list.getChildren().get(0);
        firstItem.click();

        device.wait(Until.findObject(By.textContains("Apply")), 3000);
        UiObject applyButton = device.findObject(new UiSelector().text("Apply"));
        assertTrue(applyButton.exists());
    }
}
