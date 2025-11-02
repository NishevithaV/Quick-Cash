package com.example.quick_cash.switch_role;

import static com.example.quick_cash.switch_role.ConfirmActivity.emailsMatch;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Unit tests for ConfirmActivity email matching logic
 */
public class ConfirmActivityTest {

    @Test
    public void testEmailsMatchReturnsTrueForMatchingEmails() {
        // Arrange
        String email1 = "test@example.com";
        String email2 = "test@example.com";

        // Act
        boolean result = emailsMatch(email1,email2);

        // Assert
        assertTrue("Emails should match", result);
    }

    @Test
    public void testEmailsMatchReturnsFalseForNonMatchingEmails() {
        // Arrange
        String email1 = "test@example.com";
        String email2 = "different@example.com";

        // Act
        boolean result = emailsMatch(email1,email2);

        // Assert
        assertFalse("Emails should not match", result);
    }
}
