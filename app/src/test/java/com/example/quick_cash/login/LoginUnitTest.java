package com.example.quick_cash.login;

import static org.junit.Assert.*;

import com.example.quick_cash.controllers.LoginValidator;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit test class for the Login feature.
 * Verifies input validation for email and password fields.
 */
public class LoginUnitTest {

    private LoginValidator validator;

    /**
     * Set up before running tests
     */
    @Before
    public void setUp() {
        validator = new LoginValidator();
    }

    /**
     * Test empty email returns false.
     */
    @Test
    public void testEmptyEmail_returnsFalse() {
        assertFalse("Email should be invalid when empty", validator.isEmailValid(""));
    }

    /**
     * Test invalid email format returns false.
     */
    @Test
    public void testInvalidEmailFormat_returnsFalse() {
        assertFalse("Invalid email format should fail validation", validator.isEmailValid("user@@example"));
    }

    /**
     * Test valid email format returns true.
     */
    @Test
    public void testValidEmailFormat_returnsTrue() {
        assertTrue("Valid email should pass validation", validator.isEmailValid("user@example.com"));
    }

    /**
     * Test empty password returns false.
     */
    @Test
    public void testEmptyPassword_returnsFalse() {
        assertFalse("Password should be invalid when empty", validator.isPasswordValid(""));
    }

    /**
     * Test short password returns false.
     */
    @Test
    public void testShortPassword_returnsFalse() {
        assertFalse("Password shorter than 6 chars should fail validation", validator.isPasswordValid("abc"));
    }

    /**
     * Test valid password length returns true.
     */
    @Test
    public void testValidPasswordLength_returnsTrue() {
        assertTrue("Password with 6+ chars should pass validation", validator.isPasswordValid("abc123"));
    }
}
