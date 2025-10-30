package com.example.quick_cash.login;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test class for the Login feature.
 * Verifies email and password validation along with authentication outcomes
 * to maintain consistent login behavior across the app.
 */

public class LoginUnitTest {

    private LoginValidator validator;

    @Before
    public void setUp() {
        validator = new LoginValidator();
    }

    @Test
    public void testEmptyEmail_returnsFalse() {
        boolean result = validator.isEmailValid("");
        assertFalse("Email should be invalid when empty", result);
    }

    @Test
    public void testInvalidEmailFormat_returnsFalse() {
        boolean result = validator.isEmailValid("user@@example");
        assertFalse("Invalid email format should fail validation", result);
    }

    @Test
    public void testValidEmailFormat_returnsTrue() {
        boolean result = validator.isEmailValid("user@example.com");
        assertTrue("Valid email should pass validation", result);
    }

    @Test
    public void testEmptyPassword_returnsFalse() {
        boolean result = validator.isPasswordValid("");
        assertFalse("Password should be invalid when empty", result);
    }

    @Test
    public void testShortPassword_returnsFalse() {
        boolean result = validator.isPasswordValid("abc");
        assertFalse("Password shorter than 6 chars should fail validation", result);
    }

    @Test
    public void testValidPasswordLength_returnsTrue() {
        boolean result = validator.isPasswordValid("abc123");
        assertTrue("Password with 6+ chars should pass validation", result);
    }

    @Test
    public void testSuccessfulLogin_returnsTrue() {
        // Mocking a correct login
        boolean result = validator.authenticate("user@example.com", "abc123");
        assertTrue("Correct credentials should return true", result);
    }

    @Test
    public void testIncorrectPassword_returnsFalse() {
        boolean result = validator.authenticate("user@example.com", "wrongpass");
        assertFalse("Incorrect password should fail authentication", result);
    }

    @Test
    public void testUnregisteredEmail_returnsFalse() {
        boolean result = validator.authenticate("unknown@example.com", "abc123");
        assertFalse("Unregistered email should fail authentication", result);
    }

    @Test
    public void testNullValues_doNotCrash() {
        boolean result = validator.authenticate(null, null);
        assertFalse("Null email/password should safely return false", result);
    }
}
