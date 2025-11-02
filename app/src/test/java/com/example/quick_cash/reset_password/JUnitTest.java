package com.example.quick_cash.reset_password;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import com.example.quick_cash.controllers.ResetPasswordValidator;

public class JUnitTest {

    private ResetPasswordValidator validator;

    /**
     * Set up before running tests
     */
    @Before
    public void setUp() {
        validator = new ResetPasswordValidator();
    }

    /**
     * Is in valid email.
     */
    @Test
    public void isInValidEmail() {
        assertFalse(validator.isValidEmail("alex.gmail.com"));
        assertFalse(validator.isValidEmail(""));
        assertFalse(validator.isValidEmail("alex@gmailcom"));
        assertFalse(validator.isValidEmail("  "));
    }

    /**
     * Is valid email.
     */
    @Test
    public void isValidEmail() {
        assertTrue(validator.isValidEmail("alex@gmail.com"));
        assertTrue(validator.isValidEmail("alex-g@gmail.com"));
        assertTrue(validator.isValidEmail("al3098@somedomain.ca"));
    }
}
