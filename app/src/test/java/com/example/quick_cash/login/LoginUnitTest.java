package com.example.quick_cash.login;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test class for the Login feature.
 * Verifies input validation for email and password fields.
 */
public class LoginUnitTest {

    private LoginValidator validator;

    @Before
    public void setUp() {
        validator = new LoginValidator();
    }

    @Test
    public void testEmptyEmail_returnsFalse() {
        assertFalse("Email should be invalid when empty", validator.isEmailValid(""));
    }

    @Test
    public void testInvalidEmailFormat_returnsFalse() {
        assertFalse("Invalid email format should fail validation", validator.isEmailValid("user@@example"));
    }

    @Test
    public void testValidEmailFormat_returnsTrue() {
        assertTrue("Valid email should pass validation", validator.isEmailValid("user@example.com"));
    }

    @Test
    public void testEmptyPassword_returnsFalse() {
        assertFalse("Password should be invalid when empty", validator.isPasswordValid(""));
    }

    @Test
    public void testShortPassword_returnsFalse() {
        assertFalse("Password shorter than 6 chars should fail validation", validator.isPasswordValid("abc"));
    }

    @Test
    public void testValidPasswordLength_returnsTrue() {
        assertTrue("Password with 6+ chars should pass validation", validator.isPasswordValid("abc123"));
    }
}
