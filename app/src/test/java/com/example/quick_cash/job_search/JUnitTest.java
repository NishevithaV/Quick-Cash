package com.example.quick_cash.job_search;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quick_cash.FirebaseCRUD;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

public class JUnitTest {
    private FirebaseCRUD crud;

    @Before
    public void setup() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        crud = new FirebaseCRUD(db);
    }

    @Test
    public void testFetchAllJobs() {
        Boolean isTest = true;
        ArrayList<Map<String, String>> results = crud.getJobs("", "", "", isTest);

        Map<String, String> testJob1Obj = results.get(0);
        Map<String, String> testJob2Obj = results.get(1);

        assertEquals("Real Engineer", testJob1Obj.get("name"));
        assertEquals("Some Company", testJob1Obj.get("company"));
        assertEquals("12/34/56", testJob1Obj.get("deadline"));
        assertEquals("Full-Time", testJob1Obj.get("type"));

        assertEquals("Real Accountant", testJob2Obj.get("name"));
        assertEquals("Another Company", testJob2Obj.get("company"));
        assertEquals("12/34/56", testJob2Obj.get("deadline"));
        assertEquals("Part-Time", testJob2Obj.get("type"));
    }
}
