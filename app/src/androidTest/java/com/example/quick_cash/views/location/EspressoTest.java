package com.example.quick_cash.views.location;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;


import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.GrantPermissionRule;


import android.Manifest;

import com.example.quick_cash.R;
import com.example.quick_cash.views.maps.CurrentLocationActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class EspressoTest {

    /**
     * The Activity scenario.
     */
    public ActivityScenario<CurrentLocationActivity> activityScenario;

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
        activityScenario = ActivityScenario.launch(CurrentLocationActivity.class);
    }

    /**
     * Test current Location is present.
     */
    @Test
    public void testLocationIsPresent() {

        onView(withId(R.id.locationHeader))
                .check(matches(withText("Detecting current location...")));


    }

    /**
     * Test current Location updates results.
     */
//    @Test
//    public void testLocationHeaderUpdate() {
//        Location testLocation = new Location(LocationManager.GPS_PROVIDER);
//        testLocation.setLatitude(44.65);
//        testLocation.setLongitude(-63.59);
//        LocationManager locationManager =
//                androidx.test.core.app.ApplicationProvider.getApplicationContext()
//                        .getSystemService(LocationManager.class);
//
//        String provider = LocationManager.GPS_PROVIDER;
//
//        locationManager.addTestProvider(provider, false, false, false, false,
//                    false, false, false, 0, 5);
//
//        locationManager.setTestProviderEnabled(provider, true);
//        locationManager.setTestProviderLocation(provider, testLocation);
//
//        onView(withId(R.id.locationHeader))
//                .check(matches(withText("Halifax: Canada")));
//
//    }

}