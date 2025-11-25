package com.example.quick_cash.employer;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.quick_cash.controllers.JobPostingValidator;

public class JUnitTest {
    JobPostingValidator validator;

    /**
     * Set up before running tests
     */
    @Before
    public void setup() {
        validator = new JobPostingValidator();
    }

    /**
     * Check if job title is empty.
     */
    @Test
    public void checkIfJobTitleIsEmpty() {
        assertTrue(validator.checkEmptyJobTitle(""));
    }

    /**
     * Check if job title is not empty.
     */
    @Test
    public void checkIfJobTitleIsNotEmpty() {
        assertFalse(validator.checkEmptyJobTitle("Software Developer - CSCI 3130"));
    }

    /**
     * Check if job category is valid.
     */
    @Test
    public void checkIfJobCategoryIsValid() {
        assertTrue(validator.checkValidJobCategory("AI"));
    }

    /**
     * Check if job category is invalid.
     */
//
    @Test
    public void checkIfJobCategoryIsInvalid() {
        assertFalse(validator.checkValidJobCategory("Select job category"));
    }

    /**
     * Check if application deadline is empty.
     */
    @Test
    public void checkIfApplicationDeadlineIsEmpty() {
        assertTrue(validator.checkEmptyApplicationDeadline(""));
    }

    /**
     * Check if application deadline is valid.
     */
    @Test
    public void checkIfApplicationDeadlineIsValid() {
        assertTrue(validator.checkValidApplicationDeadline("2025-12-25"));
    }

    /**
     * Check if application deadline is invalid.
     */
    @Test
    public void checkIfApplicationDeadlineIsInvalid() {
        assertFalse(validator.checkValidApplicationDeadline("2024-10-21"));
    }

    /**
     * Check if description is empty.
     */
    @Test
    public void checkIfDescriptionIsEmpty(){
        assertTrue(validator.checkEmptyJobDescription(""));
    }

    /**
     * Check if description is not empty.
     */
    @Test
    public void checkIfDescriptionIsNotEmpty() {
        assertFalse(validator.checkEmptyJobDescription("Sample description"));
    }
}