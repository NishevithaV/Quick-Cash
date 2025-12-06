package com.example.quick_cash.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.io.IOException;
import java.util.List;

public class LocationHandler {

    private static LocationHandler instance;

    private final Context context;
    private final LocationManager locationManager;

    private double latitude = 0;
    private double longitude = 0;
    private String detectedLocation = "Detecting Current Location...";

    private boolean hasLocation = false;

    private LocationListener locationListener;
    private OnLocationUpdate currentLocationCallback;

    public interface OnLocationUpdate {
        void onUpdate(String readableLocation);
    }

    /**
     * Instantiates a new Location handler.
     *
     * @param ctx the context
     */
    private LocationHandler(Context ctx) {
        this.context = ctx.getApplicationContext();
        this.locationManager =
                (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateLocation(location);
            }
        };
    }

    /**
     * Gets instance.
     *
     * @param ctx the context
     * @return the locationHandler instance
     */
    public static synchronized LocationHandler getInstance(Context ctx) {
        if (instance == null) {
            instance = new LocationHandler(ctx);
        }
        return instance;
    }

    /**
     * Sets callback.
     *
     * @param callback the callback
     */
    public void setCallback(OnLocationUpdate callback) {
        this.currentLocationCallback = callback;
    }

    /**
     * Start location updates
     */
    public void startUpdates() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0,
                locationListener
        );

        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                0,
                0,
                locationListener
        );
    }

    /**
     * updates the current location
     * @param location the location
     */
    private void updateLocation(Location location) {
        if (location == null) return;

        latitude = location.getLatitude();
        longitude = location.getLongitude();
        hasLocation = true;

        Geocoder geocoder = new Geocoder(context);

        try {
            List<Address> result = geocoder.getFromLocation(latitude, longitude, 1);

            if (result == null || result.isEmpty()) {
                detectedLocation = "Unknown Location";
            } else {
                Address addr = result.get(0);

                String city = addr.getLocality() != null ? addr.getLocality() : "Unknown";
                detectedLocation = city + ", " + addr.getCountryName();
            }
        } catch (IOException e) {
            detectedLocation = "Unknown Location";
        }

        if (currentLocationCallback != null) {
            currentLocationCallback.onUpdate(detectedLocation);
        }
    }

    /**
     * has location boolean.
     *
     * @return the boolean
     */
    public boolean hasLocation() { return hasLocation; }

    /**
     * Gets latitude.
     *
     * @return the latitude
     */
    public double getLatitude() { return latitude; }

    /**
     * Gets longitude.
     *
     * @return the longitude
     */
    public double getLongitude() { return longitude; }

    /**
     * Gets detected location.
     *
     * @return the detected location
     */
    public String getDetectedLocation() { return detectedLocation; }
}
