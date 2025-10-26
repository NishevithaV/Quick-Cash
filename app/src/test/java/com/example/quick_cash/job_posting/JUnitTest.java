package com.example.quick_cash.job_posting;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class JUnitTest {
    JobPostingValidator validator;

    @Before
    public void setup() {
        validator = new JobPostingValidator();
    }

    @Test
    public void checkIfJobTitleIsEmpty() {
        assertTrue(validator.checkEmptyJobTitle(""));
    }

    @Test
    public void checkIfJobTitleIsNotEmpty() {
        assertFalse(validator.checkEmptyJobTitle("Software Developer - CSCI 3130"));
    }

    @Test
    public void checkIfJobCategoryIsValid() {
        assertTrue(validator.checkValidJobCategory("AI"));
    }
//
    @Test
    public void checkIfJobCategoryIsInvalid() {
        assertFalse(validator.checkValidJobCategory("Select job category"));
    }

    @Test
    public void checkIfApplicationDeadlineIsEmpty() {
        assertTrue(validator.checkEmptyApplicationDeadline(""));
    }

    @Test
    public void checkIfApplicationDeadlineIsValid() {
        assertTrue(validator.checkValidApplicationDeadline("2025-11-21"));
    }

    @Test
    public void checkIfApplicationDeadlineIsInvalid() {
        assertFalse(validator.checkValidApplicationDeadline("2025-10-21"));
    }

    @Test
    public void checkIfDescriptionIsEmpty(){
        assertTrue(validator.checkEmptyJobDescription(""));
    }

    @Test
    public void checkIfDescriptionIsNotEmpty() {
        assertFalse(validator.checkEmptyJobDescription("Sample description"));
    }
//
//    @Test
//    public void checkIfEmailIsInvalid(){
//        assertFalse(validator.checkValidEmail("123.dal.ca"));
//    }
//
//    @Test
//    public void checkIfEmailIsNotFromDAL(){
//        assertFalse(validator.checkDALEmail("max.payne@usask.ca"));
//    }

}