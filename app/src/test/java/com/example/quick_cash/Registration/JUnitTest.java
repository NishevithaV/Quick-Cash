package com.example.quick_cash.Registration;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */


public class JUnitTest {

    private RegistrationActivity registrationActivity;

    public void setUp() {
        registrationActivity = new RegistrationActivity();
    }

    @Test
    public void testValidUser() {
        assertTrue(registrationActivity.ValidUser("Employee"));
        assertTrue(registrationActivity.ValidUser("Employer"));
        assertFalse(registrationActivity.ValidUser(""));
        assertFalse(registrationActivity.ValidUser(null));
    }
    public void testInValidUser() {
        assertFalse(registrationActivity.ValidUser(""));
        assertFalse(registrationActivity.ValidUser(null));
        assertFalse(registrationActivity.ValidUser("User"));
    }

    @Test
    public void testValidName() {
        assertTrue(registrationActivity.ValidName("Johnny Liver"));
    }

    @Test
    public void testInValidName() {
        assertFalse(registrationActivity.ValidName(""));
        assertFalse(registrationActivity.ValidName(null));
    }

    @Test
    public void testValidEmail() {
        assertTrue(registrationActivity.ValidEmail("user@gmail.com"));
    }

    @Test
    public void testInValidEmail() {
        assertFalse(registrationActivity.ValidEmail(""));
        assertFalse(registrationActivity.ValidEmail("usergmail.com"));
        assertFalse(registrationActivity.ValidEmail("user@dal.ca"));
        assertFalse(registrationActivity.ValidEmail(null));
    }

    @Test
    public void testValidPassword() {
        assertTrue(registrationActivity.ValidPassword("Password123!"));
    }
     @Test
    public void testInValidPassword() {
         assertFalse(registrationActivity.ValidPassword("password"));
         assertFalse(registrationActivity.ValidPassword(""));
         assertFalse(registrationActivity.ValidPassword("pass"));
         assertFalse(registrationActivity.ValidPassword(null));
         assertFalse(registrationActivity.ValidPassword("Password"));
         assertFalse(registrationActivity.ValidPassword("12345678"));
         assertFalse(registrationActivity.ValidPassword("PASSWORD123"));
         assertFalse(registrationActivity.ValidPassword("Password123"));
     }

}
