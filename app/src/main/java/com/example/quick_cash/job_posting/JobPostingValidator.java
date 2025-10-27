package com.example.quick_cash.job_posting;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class JobPostingValidator {
    public JobPostingValidator() {}

    public boolean checkEmptyJobTitle(String title) {
        return title.isEmpty();
    }

    public boolean checkValidJobCategory(String category) {
        return !category.equals("Select job category");
    }

    public boolean checkEmptyApplicationDeadline(String applicationDeadline) {
        boolean isEmpty = false;
        if (applicationDeadline == null || applicationDeadline.trim().isEmpty()) {
            isEmpty = true;
        }
        return isEmpty;
    }

    public boolean checkValidApplicationDeadline(String applicationDeadline) {
        try {
            LocalDate applicationDeadlineDate = LocalDate.parse(applicationDeadline);
            LocalDate today = LocalDate.now();

            int year = applicationDeadlineDate.getYear();
            if (year >= 2025 && year < 2030) {
                if (applicationDeadlineDate.isAfter(today)) {
                    return true;
                }
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return false;
    }

    public boolean checkEmptyJobDescription(String description) {
        return description.isEmpty();
    }
}

