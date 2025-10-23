package com.example.quick_cash.job_posting;

import static org.junit.Assert.assertTrue;

import com.example.quick_cash.FirebaseCRUD;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JUnitTest {
    private FirebaseCRUD crud;

    @Before
    public void setup() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        crud = new FirebaseCRUD(db);
    }

    @Test
    public void testPostJob() {
        Map<String, String> jobObject = new HashMap<>();
        jobObject.put("name", "testName");
        jobObject.put("deadline", "testDeadline");
        jobObject.put("category", "testCategory");
        jobObject.put("desc", "testDesc");

        assertTrue(crud.postJob(jobObject));
    }
}
