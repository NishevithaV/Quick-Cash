package com.example.quick_cash.views.employee;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import android.Manifest;
import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObject2;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quick_cash";
    private UiDevice device;
    private final String gibberish = "aspfiojhnapsfanhaivhiuhawejj;aijhnhuiawoiefh";

    @Rule
    public GrantPermissionRule permissionRule =
            GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION);

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = new Intent();
        launcherIntent.setClassName(
                launcherPackageName,
                launcherPackageName+".views.employee.JobSearchActivity"
        );
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        Assert.assertNotNull(launcherIntent);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Test elements visible.
     */
    @Test
    public void testElementsVisible() {
        UiObject header = device.findObject(new UiSelector().textContains("Find Jobs"));
        assertTrue(header.exists());
        UiObject2 locationHeader = device.findObject(By.res(launcherPackageName+":id/"+"currentLocationHeader"));
        assertNotNull(locationHeader);
        UiObject2 userSearch = device.findObject(By.res(launcherPackageName+":id/"+"userSearch"));
        assertNotNull(userSearch);
        UiObject2 catSelect = device.findObject(By.res(launcherPackageName+":id/"+"catSelect"));
        assertNotNull(catSelect);
        UiObject2 locationSelect = device.findObject(By.res(launcherPackageName+":id/"+"locationSelect"));
        assertNotNull(locationSelect);
        UiObject searchBtn = device.findObject(new UiSelector().text("Go"));
        assertTrue(searchBtn.exists());
        UiObject resultsHeader = device.findObject(new UiSelector().text("Results"));
        assertTrue(resultsHeader.exists());
        UiObject2 resultsView = device.findObject(By.res(launcherPackageName+":id/"+"resultsView"));
        assertNotNull(resultsView);
    }

    /**
     * Test search shows matching results.
     */
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

    /**
     * Test no results message shown.
     */
    @Test
    public void testNoResultsMessageShown() {
        UiObject2 input = device.findObject(By.res(launcherPackageName, "userSearch"));
        UiObject2 button = device.findObject(By.res(launcherPackageName, "searchBtn"));

        input.setText(gibberish);
        button.click();

        device.wait(Until.findObject(By.text("No Results. Try a different search")), 5000);
        UiObject noResultsHeader = device.findObject(new UiSelector().text("No Results. Try a different search"));
        assertTrue(noResultsHeader.exists());
    }

    /**
     * Test job click opens detail.
     */
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

    /**
     * AT-2: Test location search field and radius selector are visible.
     */
    @Test
    public void testLocationSearchFieldAndRadiusSelectorVisible() {
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        assertNotNull(locationSearchField);
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        assertNotNull(radiusSelect);
    }

    /**
     * AT-2: Test searching by location with radius.
     */
    @Test
    public void testSearchByLocationWithRadius() throws UiObjectNotFoundException {
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax, NS");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        
        device.wait(Until.hasObject(By.text("10 km")), 2000);
        UiObject2 radius = device.findObject(By.text("10 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait for geocoding and results
        device.wait(Until.hasObject(By.text("Results")), 5000);
        UiObject resultsHeader = device.findObject(new UiSelector().text("Results"));
        assertTrue(resultsHeader.exists());
    }

    /**
     * AT-2: Test invalid location shows toast error.
     */
    @Test
    public void testInvalidLocationShowsError() {
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText(gibberish);
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("10 km")), 2000);
        UiObject2 radius = device.findObject(By.text("10 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait for toast to appear (toast message should show)
        device.wait(Until.hasObject(By.text("Unable to find location. Please try a different address.")), 3000);
    }
}
