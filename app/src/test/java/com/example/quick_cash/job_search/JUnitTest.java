package com.example.quick_cash.job_search;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.quick_cash.Models.Job;
import com.example.quick_cash.Utils.JobSearchHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class JUnitTest {

    private JobSearchHandler searcher;
    private List<Job> sampleJobs;

    @Before
    public void setup() {
        sampleJobs = new ArrayList<>();
        sampleJobs.add(new Job("Android Developer", "Tech", "2025-12-31", "Develop Android apps", "user1"));
        sampleJobs.add(new Job("Nurse", "Health", "2025-11-30", "Work in hospital", "user2"));
        sampleJobs.add(new Job("Math Teacher", "Education", "2025-10-15", "Teach math", "user3"));
        searcher = new JobSearchHandler(sampleJobs);
    }

    @Test
    public void testAllJobsOnInit() {
        assertEquals(searcher.getAllJobs(""), sampleJobs);
    }

    @Test
    public void testFilterByKeyword() {

    }

    @Test
    public void testFilterByCategory() {

    }

    @Test
    public void testFilterNoResults() {

    }
}
