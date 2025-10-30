
package com.example.quick_cash.reset_password;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class JUnitTest {

    private ResetPasswordLogic service;

    private static class TestCallback implements ResetPasswordLogic.StatusCallback {
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
        
        service = new ResetPasswordLogic();
    }

    @Test
    public void isInValidEmail() {
        assertFalse(service.isValidEmail("alex.gmail.com"));
        assertFalse(service.isValidEmail(""));
        assertFalse(service.isValidEmail("alex@gmailcom"));
        assertFalse(service.isValidEmail("  "));
    }

    @Test
    public void isValidEmail() {
        assertTrue(service.isValidEmail("alex@gmail.com"));
    }

    @Test
    public void sendResetLink_invalidEmail_returnsError() {
        TestCallback callback = new TestCallback();
        service.sendResetLink("invalidEmail@gmail", callback);
        assertTrue(callback.callbackTriggered);
        assertFalse(callback.operationSucceeded);
        assertNotNull(callback.resultMessage);
    }
}