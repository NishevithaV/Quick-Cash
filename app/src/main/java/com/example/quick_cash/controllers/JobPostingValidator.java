package com.example.quick_cash.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class JobPostingValidator {
    /**
     * Instantiates a new Job posting validator.
     */
    public JobPostingValidator() {
        // Default Constructor
    }

    /**
     * Check empty job title boolean.
     *
     * @param title the title
     * @return the boolean
     */
    public boolean checkEmptyJobTitle(String title) {
        return title.isEmpty();
    }

    /**
     * Check valid job category boolean.
     *
     * @param category the category
     * @return the boolean
     */
    public boolean checkValidJobCategory(String category) {
        return !category.equals("Select job category");
    }

    /**
     * Check empty application deadline boolean.
     *
     * @param applicationDeadline the application deadline
     * @return the boolean
     */
    public boolean checkEmptyApplicationDeadline(String applicationDeadline) {
        boolean isEmpty = false;
        if (applicationDeadline == null || applicationDeadline.trim().isEmpty()) {
            isEmpty = true;
        }
        return isEmpty;
    }

    /**
     * Check valid application deadline boolean.
     *
     * @param applicationDeadline the application deadline
     * @return the boolean
     */
    public boolean checkValidApplicationDeadline(String applicationDeadline) {
        try {
            LocalDate applicationDeadlineDate = LocalDate.parse(applicationDeadline);
            LocalDate today = LocalDate.now();

            int year = applicationDeadlineDate.getYear();
            if (year >= 2025 && year < 2030 && applicationDeadlineDate.isAfter(today)) {
                    return true;
                }
        } catch (DateTimeParseException e) {
            return false;
        }
        return false;
    }

    /**
     * Check empty job description boolean.
     *
     * @param description the description
     * @return the boolean
     */
    public boolean checkEmptyJobDescription(String description) {
        return description.isEmpty();
    }
}

