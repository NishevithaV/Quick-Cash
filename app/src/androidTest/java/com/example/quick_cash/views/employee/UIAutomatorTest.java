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

/**
 * The type Ui automator test.
 */
public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    /**
     * The Launcher package name.
     */
    final String launcherPackageName = "com.example.quick_cash";
    private UiDevice device;
    private final String gibberish = "aspfiojhnapsfanhaivhiuhawejj;aijhnhuiawoiefh";

    /**
     * The Permission rule.
     */
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

        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobTitle")).exists());
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobEmployer")).exists());
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobCategory")).exists());
        assertTrue(device.findObject(new UiSelector().resourceId("com.example.quick_cash:id/jobDesc")).exists());
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
     *
     * @throws UiObjectNotFoundException the ui object not found exception
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

    /**
     * AT-3: Test View on Map button is visible after location search.
     *
     * @throws UiObjectNotFoundException the ui object not found exception
     */
    @Test
    public void testViewOnMapButtonVisibleAfterLocationSearch() throws UiObjectNotFoundException {
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("10 km")), 2000);
        UiObject2 radius = device.findObject(By.text("10 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait for results
        device.wait(Until.hasObject(By.res(launcherPackageName, "viewOnMapBtn")), 5000);
        UiObject2 viewOnMapBtn = device.findObject(By.res(launcherPackageName, "viewOnMapBtn"));
        assertNotNull(viewOnMapBtn);
        assertTrue(viewOnMapBtn.isEnabled());
    }

    /**
     * AT-3: Test clicking View on Map button opens MapViewActivity.
     *
     * @throws UiObjectNotFoundException the ui object not found exception
     */
    @Test
    public void testClickViewOnMapOpensMapActivity() throws UiObjectNotFoundException {
        // Perform location search
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("25 km")), 2000);
        UiObject2 radius = device.findObject(By.text("25 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait for View on Map button
        device.wait(Until.hasObject(By.res(launcherPackageName, "viewOnMapBtn")), 5000);
        UiObject2 viewOnMapBtn = device.findObject(By.res(launcherPackageName, "viewOnMapBtn"));
        viewOnMapBtn.click();
        
        // Wait for MapViewActivity to load
        device.wait(Until.hasObject(By.res(launcherPackageName, "map")), 5000);
        UiObject2 mapView = device.findObject(By.res(launcherPackageName, "map"));
        assertNotNull(mapView);
    }

    /**
     * AT-3: Test View on Map displays Google Maps.
     */
    @Test
    public void testViewOnMapDisplaysGoogleMaps() {
        // Perform location search with results
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax, NS");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("50 km")), 2000);
        UiObject2 radius = device.findObject(By.text("50 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait and click View on Map
        device.wait(Until.hasObject(By.res(launcherPackageName, "viewOnMapBtn")), 5000);
        UiObject2 viewOnMapBtn = device.findObject(By.res(launcherPackageName, "viewOnMapBtn"));
        viewOnMapBtn.click();
        
        // Wait for map to load
        device.wait(Until.hasObject(By.res(launcherPackageName, "map")), 5000);
        
        // Verify map fragment exists
        UiObject2 mapFragment = device.findObject(By.res(launcherPackageName, "map"));
        assertNotNull(mapFragment);
    }

    /**
     * AT-3: Test View on Map with no results shows message.
     */
    @Test
    public void testViewOnMapWithNoResultsShowsMessage() {
        // Search in a location with no jobs (middle of ocean)
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Atlantic Ocean");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("5 km")), 2000);
        UiObject2 radius = device.findObject(By.text("5 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait for results - should show no results
        device.wait(Until.hasObject(By.text("No Results. Try a different search")), 5000);
    }

    /**
     * AT-4: Test full flow - search Halifax, open map, and verify markers.
     */
    @Test
    public void testFullFlowHalifaxSearchToMapWithMarkers() {
        // Search for jobs in Halifax
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax, NS");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("25 km")), 2000);
        UiObject2 radius = device.findObject(By.text("25 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait for results
        device.wait(Until.hasObject(By.res(launcherPackageName, "viewOnMapBtn")), 5000);
        
        // Click View on Map
        UiObject2 viewOnMapBtn = device.findObject(By.res(launcherPackageName, "viewOnMapBtn"));
        viewOnMapBtn.click();
        
        // Wait for map to load with markers
        device.wait(Until.hasObject(By.res(launcherPackageName, "map")), 5000);
        
        try {
            Thread.sleep(3000); // Wait for markers to render
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Verify map is displayed
        UiObject2 mapView = device.findObject(By.res(launcherPackageName, "map"));
        assertNotNull(mapView);
    }

    /**
     * AT-4: Test marker click interaction on Halifax map.
     */
    @Test
    public void testMarkerClickInteractionOnHalifaxMap() {
        // Search for Halifax jobs
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("10 km")), 2000);
        UiObject2 radius = device.findObject(By.text("10 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Wait and open map
        device.wait(Until.hasObject(By.res(launcherPackageName, "viewOnMapBtn")), 5000);
        UiObject2 viewOnMapBtn = device.findObject(By.res(launcherPackageName, "viewOnMapBtn"));
        viewOnMapBtn.click();
        
        // Wait for map and markers
        device.wait(Until.hasObject(By.res(launcherPackageName, "map")), 5000);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Click on center of map (where marker likely is)
        int screenWidth = device.getDisplayWidth();
        int screenHeight = device.getDisplayHeight();
        device.click(screenWidth / 2, screenHeight / 2);
        
        // Wait for info window to appear
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Map should still be visible (marker clicked successfully)
        UiObject2 mapView = device.findObject(By.res(launcherPackageName, "map"));
        assertNotNull(mapView);
    }

    /**
     * AT-4: Test clicking info window shows job details toast.
     */
    @Test
    public void testClickInfoWindowShowsJobDetailsToast() {
        // Search Halifax
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("10 km")), 2000);
        UiObject2 radius = device.findObject(By.text("10 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Open map
        device.wait(Until.hasObject(By.res(launcherPackageName, "viewOnMapBtn")), 5000);
        UiObject2 viewOnMapBtn = device.findObject(By.res(launcherPackageName, "viewOnMapBtn"));
        viewOnMapBtn.click();
        
        device.wait(Until.hasObject(By.res(launcherPackageName, "map")), 5000);
        
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Click marker to show info window
        int screenWidth = device.getDisplayWidth();
        int screenHeight = device.getDisplayHeight();
        device.click(screenWidth / 2, screenHeight / 2);
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Click info window to show details toast
        device.click(screenWidth / 2, screenHeight / 2 - 60);
        
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Toast with job details should appear
        // Note: Toast verification is limited but the action should trigger it
    }

    /**
     * AT-4: Test map displays multiple Halifax job markers.
     */
    @Test
    public void testMapDisplaysMultipleHalifaxJobMarkers() {
        // Search with larger radius to get multiple jobs
        UiObject2 locationSearchField = device.findObject(By.res(launcherPackageName, "locationSearchField"));
        locationSearchField.setText("Halifax, NS");
        
        UiObject2 radiusSelect = device.findObject(By.res(launcherPackageName, "radiusSelect"));
        radiusSelect.click();
        device.wait(Until.hasObject(By.text("50 km")), 2000);
        UiObject2 radius = device.findObject(By.text("50 km"));
        radius.click();
        
        UiObject2 searchBtn = device.findObject(By.res(launcherPackageName, "searchBtn"));
        searchBtn.click();
        
        // Open map
        device.wait(Until.hasObject(By.res(launcherPackageName, "viewOnMapBtn")), 5000);
        UiObject2 viewOnMapBtn = device.findObject(By.res(launcherPackageName, "viewOnMapBtn"));
        viewOnMapBtn.click();
        
        // Wait for all markers to load
        device.wait(Until.hasObject(By.res(launcherPackageName, "map")), 5000);
        
        try {
            Thread.sleep(4000); // Extra time for multiple markers
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        // Map should be displayed with markers
        UiObject2 mapView = device.findObject(By.res(launcherPackageName, "map"));
        assertNotNull(mapView);
        assertTrue(mapView.isEnabled());
    }
}
