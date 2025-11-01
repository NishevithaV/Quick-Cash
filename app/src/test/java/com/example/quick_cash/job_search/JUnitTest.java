package com.example.quick_cash.job_search;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.example.quick_cash.Models.Job;
import com.example.quick_cash.Utils.JobSearchHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
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
    private final String gibberish = "aspfiojhnapsfanhaivhiuhawejj;aijhnhuiawoiefh";

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

    @Test
    public void testAllJobsOnInit() {
        assertEquals(searcher.getAllJobs("", ""), sampleJobs);
    }

    @Test
    public void testFilterByKeywordAndCategory() {
        assertEquals(searcher.getAllJobs("Android", "Tech"), androidArr);
        assertEquals(searcher.getAllJobs("Hospital", "Health"), nurseArr);
        assertEquals(searcher.getAllJobs("English", "Education"), englishTeachArr);
    }

    @Test
    public void testFilterByKeyword() {
        assertEquals(searcher.getAllJobs("Apps", ""), androidArr);
        assertEquals(searcher.getAllJobs("Hospital", ""), nurseArr);
        assertEquals(searcher.getAllJobs("teach", ""), educationArr);
    }

    @Test
    public void testFilterByCategory() {
        assertEquals(searcher.getAllJobs("", "Tech"), androidArr);
        assertEquals(searcher.getAllJobs("", "Health"), nurseArr);
        assertEquals(searcher.getAllJobs("", "Education"), educationArr);
    }

    @Test
    public void testFilterNoResults() {
        assertEquals(searcher.getAllJobs(gibberish, "Tech"), emptyArr);
        assertEquals(searcher.getAllJobs("Dev", gibberish), emptyArr);
    }
}
