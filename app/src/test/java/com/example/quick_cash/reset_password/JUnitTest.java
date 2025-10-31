package com.example.quick_cash.reset_password;

import com.example.quick_cash.FirebaseCrud.ResetPasswordCRUD;

import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;

public class JUnitTest {

    private ResetPasswordLogic service;
    private ResetPasswordCRUD crudMock;

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
        MockitoAnnotations.openMocks(this);
        // inject a mock so we NEVER call Firebase in JVM tests
        crudMock = mock(ResetPasswordCRUD.class);
        service = new ResetPasswordLogic(crudMock);
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

    @Test
    public void sendResetLink_validEmail_returnsSuccess() {
        // arrange: valid email, but we do not stub the mock yet
        TestCallback callback = new TestCallback();

        service.sendResetLink("alex@gmail.com", callback);

        // this will fail right now because no stub is set, so callback never fires
        assertTrue("callback should be called", callback.callbackTriggered);
        assertTrue("operation should be successful", callback.operationSucceeded);
        assertEquals("reset email sent!",
                callback.resultMessage == null ? null : callback.resultMessage.toLowerCase());
    }
}
