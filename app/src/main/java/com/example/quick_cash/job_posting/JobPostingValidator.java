package com.example.quick_cash.job_posting;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class JobPostingValidator {
    public JobPostingValidator() {
        //default constructor
    }

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
        boolean isValid = false;
        try {
            LocalDate applicationDeadlineDate = LocalDate.parse(applicationDeadline);
            LocalDate today = LocalDate.now();

            int year = applicationDeadlineDate.getYear();
            if (year >= 2025 && year < 2030) {
                if (applicationDeadlineDate.isAfter(today)) {
                    isValid = true;
                }
            }
        } catch (DateTimeParseException e) {
            return false;
        }
        return isValid;
    }

    public boolean checkEmptyJobDescription(String description) {
        return description.isEmpty();
    }
}

//
//    public boolean checkDALEmail(String email) {
//        if (email == null || email.trim().isEmpty()) {
//            return false;
//        }
//
//        int indexOfAtSymbol = email.indexOf('@');
//        if (indexOfAtSymbol <= 0 || indexOfAtSymbol != email.lastIndexOf('@')) {
//            return false;
//        }
//
//        String beforeAt = email.substring(0, indexOfAtSymbol);
//        String afterAt = email.substring(indexOfAtSymbol + 1);
//
//        if (beforeAt.isEmpty() || afterAt.isEmpty()) {
//            return false;
//        }
//
//        return afterAt.equalsIgnoreCase("dal.ca");
//    }

