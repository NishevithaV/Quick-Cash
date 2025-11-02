package com.example.quick_cash.registration;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

import com.example.quick_cash.controllers.RegistrationValidator;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class JUnitTest {

    private RegistrationValidator registrationValidator;


    @Before
    public void setUp() {
        
        registrationValidator = new RegistrationValidator();
    }

    @Test
    public void testValidUser() {
        assertTrue(registrationValidator.validUser("Employee"));
        assertTrue(registrationValidator.validUser("Employer"));
        assertFalse(registrationValidator.validUser(""));
        assertFalse(registrationValidator.validUser(null));
    }

    @Test
    public void testInValidUser() {
        assertFalse(registrationValidator.validUser(""));
        assertFalse(registrationValidator.validUser(null));
        assertFalse(registrationValidator.validUser("User"));
    }

    @Test
    public void testValidName() {
        assertTrue(registrationValidator.validName("Johnny Liver"));
    }

    @Test
    public void testInValidName() {
        assertFalse(registrationValidator.validName(""));
        assertFalse(registrationValidator.validName(null));
    }

    @Test
    public void testValidEmail() {

        assertTrue(registrationValidator.validEmail("user@gmail.com"));
    }

    @Test
    public void testInValidEmail() {
        assertFalse(registrationValidator.validEmail(""));
        assertFalse(registrationValidator.validEmail("usergmail.com"));
        assertTrue(registrationValidator.validEmail("user@dal.ca"));
        assertFalse(registrationValidator.validEmail(null));
    }

    @Test
    public void testValidPassword() {

        assertTrue(registrationValidator.validPassword("Password123!"));
    }
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
