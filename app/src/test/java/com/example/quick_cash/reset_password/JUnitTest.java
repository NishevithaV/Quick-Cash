package com.example.quick_cash.reset_password;


import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class JUnitTest {

    private ResetPasswordLogic validator;

    @Before
    public void setUp() {
        validator = new ResetPasswordLogic();
    }

    @Test
    public void isInValidEmail() {
        assertFalse(validator.isValidEmail("alex.gmail.com"));
        assertFalse(validator.isValidEmail(""));
        assertFalse(validator.isValidEmail("alex@gmailcom"));
        assertFalse(validator.isValidEmail("  "));
    }

    @Test
    public void isValidEmail() {
        assertTrue(validator.isValidEmail("alex@gmail.com"));
        assertTrue(validator.isValidEmail("alex-g@gmail.com"));
        assertTrue(validator.isValidEmail("al3098@somedomain.ca"));
    }
}
