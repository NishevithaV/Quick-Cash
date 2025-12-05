package com.example.quick_cash.employee;
import static org.junit.Assert.*;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.example.quick_cash.utils.FirebaseCRUD.Users;
import com.example.quick_cash.utils.SubmitApplicationHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


public class SubmitApplicationJUnitTest {

    String jobID;
    String jobID2;
    String uID;
    SubmitApplicationHandler appHandler;

    @Before
    public void setUp() {
        uID = "testUserID";
        jobID = "testJobID";
        jobID2 = "testJobIDalreadyapplied";
        Users mockUsers = Mockito.mock(Users.class);
        Applications mockApps = Mockito.mock(Applications.class);
        appHandler = new SubmitApplicationHandler(mockUsers, mockApps);
    }

    @Test
    public void testSaveApplicationSuccess() {
        final String[] result = new String[1];
        appHandler.submitApp(uID, jobID, "My cover letter", new SubmitApplicationHandler.SubmitCallback() {
            @Override
            public void onCallback(String msg) {
                result[0] = msg;
            }
        });
        assertEquals(SubmitApplicationHandler.APPLICATION_SUCCESS, result[0]); // AT-5: should be marked as applied
    }

    @Test
    public void testSaveApplicationAlreadyApplied() {
        final String[] result = new String[1];
        appHandler.submitApp(uID, jobID2, "My cover letter", new SubmitApplicationHandler.SubmitCallback() {
            @Override
            public void onCallback(String msg) {
                result[0] = msg;
            }
        });
        assertEquals(SubmitApplicationHandler.ALREADY_APPLIED, result[0]); // AT-5: should be marked as applied
    }
}
