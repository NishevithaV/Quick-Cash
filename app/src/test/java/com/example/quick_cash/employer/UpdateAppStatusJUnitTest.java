package com.example.quick_cash.employer;


import static org.junit.Assert.assertEquals;

import com.example.quick_cash.models.Application;
import com.example.quick_cash.utils.ApplicationsFilterHandler;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UpdateAppStatusJUnitTest {
    /**
     * Test filtering by status logic
     */
    @Test
    public void testFilterByStatus() {
        ArrayList<Application> apps = new ArrayList<>();

        Application app1 = new Application("user001","I am very interested in this position.", "pending", "job001", "app001");
        apps.add(app1);
        assertEquals("pending", app1.getStatus());
        app1.setStatus("accepted");
        assertEquals("accepted", app1.getStatus());
    }
}
