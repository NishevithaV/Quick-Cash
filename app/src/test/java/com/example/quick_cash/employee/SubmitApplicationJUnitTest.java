package com.example.quick_cash.employee;
import static org.junit.Assert.*;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.example.quick_cash.utils.FirebaseCRUD.Users;
import com.example.quick_cash.utils.SubmitApplicationHandler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;


/**
 * The type Submit application j unit test.
 */
public class SubmitApplicationJUnitTest {

    /**
     * The Job id.
     */
    String jobID;
    /**
     * The Job id 2.
     */
    String jobID2;
    /**
     * The U id.
     */
    String uID;
    /**
     * The App handler.
     */
    SubmitApplicationHandler appHandler;

    /**
     * Sets up.
     */
    @Before
    public void setUp() {
        uID = "testUserID";
        jobID = "testJobID";
        jobID2 = "testJobIDalreadyapplied";
        Users mockUsers = Mockito.mock(Users.class);
        Applications mockApps = Mockito.mock(Applications.class);
        appHandler = new SubmitApplicationHandler(mockUsers, mockApps);
    }

    /**
     * Test save application success.
     */
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

    /**
     * Test save application already applied.
     */
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
