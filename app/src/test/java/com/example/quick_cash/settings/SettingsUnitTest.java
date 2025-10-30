package com.example.quick_cash.settings;

import static org.junit.Assert.*;

import com.example.quick_cash.Models.User;

import org.junit.Before;
import org.junit.Test;

public class SettingsUnitTest {
    private User testUser;

    @Before
    public void setUp() {
        testUser = new User("employer", "test@example.com", "Test User");
    }

    @Test
    public void testUserRoleSwitch() {
        assertEquals("employer", testUser.getType());
        String newRole = testUser.getType().equals("employer") ? "employee" : "employer";
        assertEquals("employee", newRole);
    }

    @Test
    public void testEmailVerification() {
        String correctEmail = "test@example.com";
        assertEquals(testUser.getEmail(), correctEmail);
        String wrongEmail = "wrong@example.com";
        assertNotEquals(testUser.getEmail(), wrongEmail);
    }

    @Test
    public void testEmailMismatchToast() {
        String entered = "wrong@example.com";
        boolean match = entered.equals(testUser.getEmail());
        assertFalse(match);
    }

    @Test
    public void testRoleUpdateOnEmailMatch() {
        String entered = "test@example.com";
        if (entered.equals(testUser.getEmail())) {
            testUser.setType("employee");
        }
        assertEquals("employee", testUser.getType());
    }

    @Test
    public void testRoleSwitchSuccessToast() {
        //
    }

    @Test
    public void testDashboardRefreshOnRoleSwitch() {
        // Not sure here
    }
}