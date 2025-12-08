package com.example.quick_cash.registration;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.example.quick_cash.controllers.RegistrationValidator;

/**
 * The type J unit test.
 */
public class JUnitTest {

    private RegistrationValidator registrationValidator;


    /**
     * Set up before running tests
     */
    @Before
    public void setUp() {
        
        registrationValidator = new RegistrationValidator();
    }

    /**
     * Test valid user.
     */
    @Test
    public void testValidUser() {
        assertTrue(registrationValidator.validUser("Employee"));
        assertTrue(registrationValidator.validUser("Employer"));
        assertFalse(registrationValidator.validUser(""));
        assertFalse(registrationValidator.validUser(null));
    }

    /**
     * Test in valid user.
     */
    @Test
    public void testInValidUser() {
        assertFalse(registrationValidator.validUser(""));
        assertFalse(registrationValidator.validUser(null));
        assertFalse(registrationValidator.validUser("User"));
    }

    /**
     * Test valid name.
     */
    @Test
    public void testValidName() {
        assertTrue(registrationValidator.validName("Johnny Liver"));
    }

    /**
     * Test in valid name.
     */
    @Test
    public void testInValidName() {
        assertFalse(registrationValidator.validName(""));
        assertFalse(registrationValidator.validName(null));
    }

    /**
     * Test valid email.
     */
    @Test
    public void testValidEmail() {

        assertTrue(registrationValidator.validEmail("user@gmail.com"));
    }

    /**
     * Test in valid email.
     */
    @Test
    public void testInValidEmail() {
        assertFalse(registrationValidator.validEmail(""));
        assertFalse(registrationValidator.validEmail("usergmail.com"));
        assertTrue(registrationValidator.validEmail("user@dal.ca"));
        assertFalse(registrationValidator.validEmail(null));
    }

    /**
     * Test valid password.
     */
    @Test
    public void testValidPassword() {

        assertTrue(registrationValidator.validPassword("Password123!"));
    }

    /**
     * Test in valid password.
     */
    @Test
    public void testInValidPassword() {
         setUp();
         assertFalse(registrationValidator.validPassword("password"));
         assertFalse(registrationValidator.validPassword(""));
         assertFalse(registrationValidator.validPassword("pass"));
         assertFalse(registrationValidator.validPassword(null));
         assertFalse(registrationValidator.validPassword("Password"));
         assertFalse(registrationValidator.validPassword("12345678"));
         assertFalse(registrationValidator.validPassword("PASSWORD123"));
         assertFalse(registrationValidator.validPassword("Password123"));
     }

}
