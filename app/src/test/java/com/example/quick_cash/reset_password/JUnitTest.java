
package com.example.quick_cash.reset_password;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JUnitTest {

    private ResetPasswordService service;

    private static class TestCallback implements ResetPasswordService.StatusCallback {
        boolean callbackTriggered = false;
        boolean operationSucceeded;
        String resultMessage;

        @Override
        public void onComplete(boolean success, String message) {
            this.callbackTriggered = true;
            this.operationSucceeded = success;
            this.resultMessage = message;
        }
    }

    @Before
    public void setUp() {
        service = new ResetPasswordService();
    }

    @Test
    public void isValidEmail_empty_false() {
        assertFalse(service.isValidEmail(""));
    }

    @Test
    public void isValidEmail_whitespace_false() {
        assertFalse(service.isValidEmail("   "));
    }

    @Test
    public void isValidEmail_noAt_false() {
        assertFalse(service.isValidEmail("user.example.com"));
    }

    @Test
    public void isValidEmail_good_true() {
        assertTrue(service.isValidEmail("user@example.com"));
    }

    @Test
    public void sendResetLink_invalidEmail_callsErrorImmediately() {
        TestCallback callback = new TestCallback();
        service.sendResetLink("bad", callback);
        assertTrue(callback.callbackTriggered);
        assertFalse(callback.operationSucceeded);
        assertNotNull(callback.resultMessage);
    }
}