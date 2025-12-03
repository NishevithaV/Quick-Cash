package com.example.quick_cash.views.maps;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Job;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;

public class MapViewActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<Job> jobs;
    private TextView noJobsMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        noJobsMessage = findViewById(R.id.noJobsMessage);

        // Get jobs from intent
        Serializable serializable = getIntent().getSerializableExtra("jobs");
        if (serializable instanceof ArrayList<?>) {
            jobs = (ArrayList<Job>) serializable;
        } else {
            jobs = new ArrayList<>();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Filter jobs that have valid coordinates
        ArrayList<Job> jobsWithCoords = new ArrayList<>();
        for (Job job : jobs) {
            if (job.getLatitude() != 0.0 && job.getLongitude() != 0.0) {
                jobsWithCoords.add(job);
            }
        }

        // Check if there are any jobs to display
        if (jobsWithCoords.isEmpty()) {
            noJobsMessage.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No jobs found in this location", Toast.LENGTH_LONG).show();
            return;
        }

        // Add markers for each job
        LatLngBounds.Builder boundsBuilder = new LatLngBounds.Builder();
        
        for (Job job : jobsWithCoords) {
            LatLng position = new LatLng(job.getLatitude(), job.getLongitude());
            
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(position)
                    .title(job.getTitle())
                    .snippet(job.getLocation()));
            
            // Store job in marker tag for info window
            if (marker != null) {
                marker.setTag(job);
            }
            
            boundsBuilder.include(position);
        }

        // Set up info window click listener
        mMap.setOnInfoWindowClickListener(marker -> {
            Job job = (Job) marker.getTag();
            if (job != null) {
                String message = "Category: " + job.getCategory() + "\n" +
                                 "Location: " + job.getLocation() + "\n" +
                                 "Description: " + job.getDesc();
                Toast.makeText(MapViewActivity.this, message, Toast.LENGTH_LONG).show();
            }
        });

        // Set up custom info window adapter
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(@NonNull Marker marker) {
                return null; // Use default frame
            }

            @Override
            public View getInfoContents(@NonNull Marker marker) {
                // Let default info window show title and snippet
                return null;
            }
        });

        // Zoom camera to show all markers
        try {
            LatLngBounds bounds = boundsBuilder.build();
            int padding = 100; // Padding around markers in pixels
            mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, padding));
        } catch (IllegalStateException e) {
            // If only one marker, zoom to it directly
            if (jobsWithCoords.size() == 1) {
                Job job = jobsWithCoords.get(0);
                LatLng position = new LatLng(job.getLatitude(), job.getLongitude());
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
            }
        }
    }
}
