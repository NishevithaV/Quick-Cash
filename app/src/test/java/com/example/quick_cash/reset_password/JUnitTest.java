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
        TestCallback callback = new TestCallback();

        doAnswer(inv -> {
            com.example.quick_cash.FirebaseCrud.ResetPasswordCRUD.BoolCallback cb = inv.getArgument(1);
            cb.onResult(true); // pretend email exists
            return null;
        }).when(crudMock).checkEmailExists(eq("alex@gmail.com"),
                any(com.example.quick_cash.FirebaseCrud.ResetPasswordCRUD.BoolCallback.class));

        doAnswer(inv -> {
            com.example.quick_cash.FirebaseCrud.ResetPasswordCRUD.BoolCallback cb = inv.getArgument(1);
            cb.onResult(true); // pretend reset email sent
            return null;
        }).when(crudMock).sendResetEmail(eq("alex@gmail.com"),
                any(com.example.quick_cash.FirebaseCrud.ResetPasswordCRUD.BoolCallback.class));

        service.sendResetLink("alex@gmail.com", callback);

        assertTrue("callback should be called", callback.callbackTriggered);
        assertTrue("operation should be successful", callback.operationSucceeded);
        assertEquals("reset email sent!", callback.resultMessage.toLowerCase());
    }
}
