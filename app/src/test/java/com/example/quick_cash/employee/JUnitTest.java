package com.example.quick_cash.employee;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.quick_cash.models.Job;
import com.example.quick_cash.utils.JobSearchHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JUnitTest {

    private JobSearchHandler searcher;
    private List<Job> sampleJobs;
    private List<Job> androidArr;
    private List<Job> nurseArr;
    private List<Job> mathTeachArr;
    private List<Job> englishTeachArr;
    private List<Job> educationArr;
    private List<Job> emptyArr;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        sampleJobs = new ArrayList<>();
        sampleJobs.add(new Job("Android Developer", "Tech", "2025-12-31", "Develop Android apps", "user1"));
        sampleJobs.add(new Job("Nurse", "Health", "2025-11-30", "Work in hospital", "user2"));
        sampleJobs.add(new Job("Math Teacher", "Education", "2025-10-15", "Teach math", "user3"));
        sampleJobs.add(new Job("English Teacher", "Education", "2025-10-15", "Teach English", "user4"));
        searcher = new JobSearchHandler(sampleJobs);

        androidArr = new ArrayList<Job>(Collections.singletonList(sampleJobs.get(0)));
        nurseArr = new ArrayList<Job>(Collections.singletonList(sampleJobs.get(1)));
        mathTeachArr = new ArrayList<Job>(Collections.singletonList(sampleJobs.get(2)));
        englishTeachArr = new ArrayList<Job>(Collections.singletonList(sampleJobs.get(3)));
        educationArr = new ArrayList<Job>();
        educationArr.addAll(mathTeachArr);
        educationArr.addAll(englishTeachArr);
        emptyArr = new ArrayList<Job>();
    }

    /**
     * Test all jobs on init.
     */
    @Test
    public void testAllJobsOnInit() {
        assertEquals(searcher.getAllJobs("", ""), sampleJobs);
    }

    /**
     * Test filter by keyword and category.
     */
    @Test
    public void testFilterByKeywordAndCategory() {
        assertEquals(searcher.getAllJobs("Android", "Tech"), androidArr);
        assertEquals(searcher.getAllJobs("Hospital", "Health"), nurseArr);
        assertEquals(searcher.getAllJobs("English", "Education"), englishTeachArr);
    }

    /**
     * Test filter by keyword.
     */
    @Test
    public void testFilterByKeyword() {
        assertEquals(searcher.getAllJobs("Apps", ""), androidArr);
        assertEquals(searcher.getAllJobs("Hospital", ""), nurseArr);
        assertEquals(searcher.getAllJobs("teach", ""), educationArr);
    }

    /**
     * Test filter by category.
     */
    @Test
    public void testFilterByCategory() {
        assertEquals(searcher.getAllJobs("", "Tech"), androidArr);
        assertEquals(searcher.getAllJobs("", "Health"), nurseArr);
        assertEquals(searcher.getAllJobs("", "Education"), educationArr);
    }

    /**
     * Test filter no results.
     */
    @Test
    public void testFilterNoResults() {
        String gibberish = "aspfiojhnapsfanhaivhiuhawejj;aijhnhuiawoiefh";
        assertEquals(searcher.getAllJobs(gibberish, "Tech"), emptyArr);
        assertEquals(searcher.getAllJobs("Dev", gibberish), emptyArr);
    }
}
