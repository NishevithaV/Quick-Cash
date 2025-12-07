package com.example.quick_cash.employer;


import static org.junit.Assert.assertEquals;

import com.example.quick_cash.models.Application;
import com.example.quick_cash.utils.ApplicationsFilterHandler;

import org.junit.Test;

import java.util.List;
import java.util.ArrayList;

/**
 * The type App review j unit test.
 */
public class AppReviewJUnitTest {
    /**
     * Test filtering by status logic
     */
    @Test
    public void testFilterByStatus() {
        ArrayList<Application> apps = new ArrayList<>();

        Application app1 = new Application("user001","I am very interested in this position.", "pending", "job001", "app001");
        apps.add(app1);

        ApplicationsFilterHandler filterHandler = new ApplicationsFilterHandler(apps);
        List<Application> filtered = filterHandler.getAppsByStatus("pending");

        for (Application a : filtered) {
            assertEquals("pending", a.getStatus()); // AT-4
        }
        filtered.clear();

        filtered.addAll(filterHandler.getAppsByStatus("accepted"));
        for (Application a : filtered) {
            assertEquals("accepted", a.getStatus()); // AT-4
        }
        filtered.clear();

        filtered.addAll(filterHandler.getAppsByStatus("declined"));
        for (Application a : filtered) {
            assertEquals("declined", a.getStatus()); // AT-4
        }
    }
}
