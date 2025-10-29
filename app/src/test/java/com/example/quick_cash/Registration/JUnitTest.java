package com.example.quick_cash.Registration;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

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
        assertTrue(registrationValidator.ValidUser("Employee"));
        assertTrue(registrationValidator.ValidUser("Employer"));
        assertFalse(registrationValidator.ValidUser(""));
        assertFalse(registrationValidator.ValidUser(null));
    }

    @Test
    public void testInValidUser() {
        assertFalse(registrationValidator.ValidUser(""));
        assertFalse(registrationValidator.ValidUser(null));
        assertFalse(registrationValidator.ValidUser("User"));
    }

    @Test
    public void testValidName() {
        assertTrue(registrationValidator.ValidName("Johnny Liver"));
    }

    @Test
    public void testInValidName() {
        assertFalse(registrationValidator.ValidName(""));
        assertFalse(registrationValidator.ValidName(null));
    }

    @Test
    public void testValidEmail() {

        assertTrue(registrationValidator.ValidEmail("user@gmail.com"));
    }

    @Test
    public void testInValidEmail() {
        assertFalse(registrationValidator.ValidEmail(""));
        assertFalse(registrationValidator.ValidEmail("usergmail.com"));
        assertFalse(registrationValidator.ValidEmail("user@dal.ca"));
        assertFalse(registrationValidator.ValidEmail(null));
    }

    @Test
    public void testValidPassword() {

        assertTrue(registrationValidator.ValidPassword("Password123!"));
    }
     @Test
    public void testInValidPassword() {
         setUp();
         assertFalse(registrationValidator.ValidPassword("password"));
         assertFalse(registrationValidator.ValidPassword(""));
         assertFalse(registrationValidator.ValidPassword("pass"));
         assertFalse(registrationValidator.ValidPassword(null));
         assertFalse(registrationValidator.ValidPassword("Password"));
         assertFalse(registrationValidator.ValidPassword("12345678"));
         assertFalse(registrationValidator.ValidPassword("PASSWORD123"));
         assertFalse(registrationValidator.ValidPassword("Password123"));
     }

}
