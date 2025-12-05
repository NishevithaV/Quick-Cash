package com.example.quick_cash.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class JobTest {

    private Job job;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        job = new Job();
    }

    /**
     * Test job creation with coordinates
     */
    @Test
    public void testJobCreationWithCoordinates() {
        Job jobWithCoords = new Job(
                "Software Engineer",
                "Tech",
                "Halifax, NS",
                "2025-12-31",
                "Test description",
                "user123",
                44.6488,
                -63.5752
        );
        
        assertNotNull(jobWithCoords);
        assertEquals("Software Engineer", jobWithCoords.getTitle());
        assertEquals(44.6488, jobWithCoords.getLatitude(), 0.0001);
        assertEquals(-63.5752, jobWithCoords.getLongitude(), 0.0001);
    }

    /**
     * Test job creation without coordinates defaults to 0.0
     */
    @Test
    public void testJobCreationWithoutCoordinatesDefaultsToZero() {
        Job jobWithoutCoords = new Job(
                "Backend Developer",
                "Tech",
                "Toronto, ON",
                "2025-11-30",
                "Test description",
                "user456"
        );
        
        assertEquals(0.0, jobWithoutCoords.getLatitude(), 0.0001);
        assertEquals(0.0, jobWithoutCoords.getLongitude(), 0.0001);
    }

    /**
     * Test latitude getter
     */
    @Test
    public void testGetLatitude() {
        job.setLatitude(43.6532);
        assertEquals(43.6532, job.getLatitude(), 0.0001);
    }

    /**
     * Test longitude getter
     */
    @Test
    public void testGetLongitude() {
        job.setLongitude(-79.3832);
        assertEquals(-79.3832, job.getLongitude(), 0.0001);
    }

    /**
     * Test latitude setter
     */
    @Test
    public void testSetLatitude() {
        job.setLatitude(45.5017);
        assertEquals(45.5017, job.getLatitude(), 0.0001);
    }

    /**
     * Test longitude setter
     */
    @Test
    public void testSetLongitude() {
        job.setLongitude(-73.5673);
        assertEquals(-73.5673, job.getLongitude(), 0.0001);
    }

    /**
     * Test coordinates with negative values
     */
    @Test
    public void testNegativeCoordinates() {
        job.setLatitude(-33.8688);
        job.setLongitude(151.2093);
        assertEquals(-33.8688, job.getLatitude(), 0.0001);
        assertEquals(151.2093, job.getLongitude(), 0.0001);
    }

    /**
     * Test job is serializable
     */
    @Test
    public void testJobIsSerializable() {
        Job testJob = new Job(
                "Test Job",
                "Finance",
                "Montreal, QC",
                "2025-12-15",
                "Description",
                "user789",
                45.5017,
                -73.5673
        );
        
        // If this compiles, Job implements Serializable
        assertNotNull(testJob);
        assert testJob instanceof java.io.Serializable;
    }
}