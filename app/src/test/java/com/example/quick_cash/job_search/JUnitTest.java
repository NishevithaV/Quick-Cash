package com.example.quick_cash.job_search;

import static org.junit.Assert.*;

import androidx.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;

import com.example.quick_cash.Models.Job;
import com.example.quick_cash.job_search.JobSearchActivity;

import java.util.ArrayList;
import java.util.List;

public class JUnitTest {

    private JobSearchActivity activity;
    private List<Job> sampleJobs;

    @Before
    public void setup() {
        activity = new JobSearchActivity();
        sampleJobs = new ArrayList<>();
        sampleJobs.add(new Job("Android Developer", "Tech", "2025-12-31", "Develop Android apps", "user1"));
        sampleJobs.add(new Job("Nurse", "Health", "2025-11-30", "Work in hospital", "user2"));
        sampleJobs.add(new Job("Math Teacher", "Education", "2025-10-15", "Teach math", "user3"));

        // Inject sample data
        activity.allJobs.addAll(sampleJobs);
    }

    @Test
    public void testFilterByKeyword() {
        activity.userSearch = new MockEditText("Android");
        activity.catSelect = new MockSpinner("Category");

        List<Job> result = activity.filterJobs();
        assertEquals(1, result.size());
        assertEquals("Android Developer", result.get(0).getTitle());
    }

    @Test
    public void testFilterByCategory() {
        activity.userSearch = new MockEditText(""); // empty keyword
        activity.catSelect = new MockSpinner("Health");

        List<Job> result = activity.filterJobs();
        assertEquals(1, result.size());
        assertEquals("Nurse", result.get(0).getTitle());
    }

    @Test
    public void testFilterNoResults() {
        activity.userSearch = new MockEditText("Engineer");
        activity.catSelect = new MockSpinner("Education");

        List<Job> result = activity.filterJobs();
        assertTrue(result.isEmpty());
    }

    // Mock classes for EditText and Spinner
    static class MockEditText extends android.widget.EditText {
        private final String text;

        public MockEditText(String text) {
            super(null);
            this.text = text;
        }

        @NonNull
        @Override
        public android.text.Editable getText() {
            return android.text.Editable.Factory.getInstance().newEditable(text);
        }
    }

    static class MockSpinner extends android.widget.Spinner {
        private final String selected;
        public MockSpinner(String selected) { super(null); this.selected = selected; }
        @Override public Object getSelectedItem() { return selected; }
    }
}
