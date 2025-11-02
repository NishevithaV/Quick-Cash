package com.example.quick_cash.views.switch_role;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.init;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.Intents.release;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.utils.FirebaseCRUD.Users;
import com.example.quick_cash.R;
import com.example.quick_cash.views.employee.EmployeeDashboardActivity;
import com.example.quick_cash.views.employer.EmployerDashboardActivity;
import com.example.quick_cash.views.settings.SettingsActivity;
import com.example.quick_cash.utils.SwitchRoleHandler;
import com.example.quick_cash.views.switch_role.ConfirmActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Comprehensive Espresso + Mockito integration tests for SettingsActivity
 */
@RunWith(AndroidJUnit4.class)
public class SettingsActivityEspressoTest {

    @Mock
    private FirebaseAuth mockAuth;

    @Mock
    private FirebaseUser mockUser;

    @Mock
    private FirebaseDatabase mockDatabase;

    @Mock
    private DatabaseReference mockUsersRef;

    @Mock
    private DatabaseReference mockUserIdRef;

    @Mock
    private DatabaseReference mockTypeRef;

    @Mock
    private DataSnapshot mockDataSnapshot;

    private static final String TEST_USER_ID = "testUser123";
    private static final String TEST_EMAIL = "test@example.com";
    private static final String CURRENT_ROLE_EMPLOYER = "Employer";
    private static final String CURRENT_ROLE_EMPLOYEE = "Employee";
    private static final String NEW_ROLE_EMPLOYEE = "Employee";
    private static final String NEW_ROLE_EMPLOYER = "Employer";

    /**
     * Set up before running tests
     */
    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Test switch role button is displayed.
     */
    @Test
    public void testSwitchRoleButtonIsDisplayed() {
        // Launch activity
        ActivityScenario<SettingsActivity> scenario = ActivityScenario.launch(SettingsActivity.class);

        scenario.onActivity(activity -> {
            // Inject mocked Firebase into the handler
            SwitchRoleHandler handler = activity.getSwitchRoleHandler();
            handler.setFirebaseAuth(mockAuth);
            handler.setFirebaseDatabase(mockDatabase);
        });

        // Assert - Switch Role button is displayed
        onView(withId(R.id.switchRoleButton))
                .check(matches(isDisplayed()));

        scenario.close();
    }

    /**
     * Test integration correct email updates role and redirects to dashboard.
     */
    @Test
    public void testIntegration_CorrectEmail_UpdatesRoleAndRedirectsToDashboard() {
        // Initialize Espresso Intents
        init();

        // Create a mock Users object
        Users mockUsers = mock(Users.class);

        // Mock the updateUserRole to immediately call onSuccess
        doAnswer(invocation -> {
            Users.RoleUpdateCallback callback = invocation.getArgument(2);
            callback.onSuccess();
            return null;
        }).when(mockUsers).updateUserRole(eq(TEST_USER_ID), eq(NEW_ROLE_EMPLOYEE), any(Users.RoleUpdateCallback.class));

        // Create intent to directly launch ConfirmActivity with test data
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ConfirmActivity.class);
        intent.putExtra("current_role", CURRENT_ROLE_EMPLOYER);
        intent.putExtra("new_role", NEW_ROLE_EMPLOYEE);
        intent.putExtra("current_email", TEST_EMAIL);
        intent.putExtra("user_id", TEST_USER_ID);

        // Launch ConfirmActivity and inject mock
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> activity.setUsersDb(mockUsers));

        // Enter correct email
        onView(withId(R.id.roleInputEmail))
                .perform(typeText(TEST_EMAIL), closeSoftKeyboard());

        // Click confirm button
        onView(withId(R.id.roleConfirmButton))
                .perform(click());

        // Verify that the intent to navigate to EmployeeDashboardActivity was sent
        intended(hasComponent(EmployeeDashboardActivity.class.getName()));

        // Verify that updateUserRole was called with correct parameters
        verify(mockUsers).updateUserRole(eq(TEST_USER_ID), eq(NEW_ROLE_EMPLOYEE), any(Users.RoleUpdateCallback.class));

        scenario.close();
        release();
    }


    /**
     * Test firebase mock employer to employee redirects to employee dashboard.
     */
    @Test
    public void testFirebaseMock_EmployerToEmployee_RedirectsToEmployeeDashboard() {
        // Initialize Espresso Intents
        init();

        // Create a mock Users object
        Users mockUsers = mock(Users.class);

        // Mock successful role update
        doAnswer(invocation -> {
            Users.RoleUpdateCallback callback = invocation.getArgument(2);
            callback.onSuccess();
            return null;
        }).when(mockUsers).updateUserRole(eq(TEST_USER_ID), eq(NEW_ROLE_EMPLOYEE), any(Users.RoleUpdateCallback.class));

        // Create intent for employer switching to employee
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ConfirmActivity.class);
        intent.putExtra("current_role", CURRENT_ROLE_EMPLOYER);
        intent.putExtra("new_role", NEW_ROLE_EMPLOYEE);
        intent.putExtra("current_email", TEST_EMAIL);
        intent.putExtra("user_id", TEST_USER_ID);

        // Launch ConfirmActivity and inject mock
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> activity.setUsersDb(mockUsers));

        // Enter correct email and confirm
        onView(withId(R.id.roleInputEmail))
                .perform(typeText(TEST_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.roleConfirmButton))
                .perform(click());

        // Verify navigation to EmployeeDashboardActivity
        intended(hasComponent(EmployeeDashboardActivity.class.getName()));

        scenario.close();
        release();
    }

    /**
     * Test firebase mock employee to employer redirects to employer dashboard.
     */
    @Test
    public void testFirebaseMock_EmployeeToEmployer_RedirectsToEmployerDashboard() {
        // Initialize Espresso Intents
        init();

        // Create a mock Users object
        Users mockUsers = mock(Users.class);

        // Mock successful role update
        doAnswer(invocation -> {
            Users.RoleUpdateCallback callback = invocation.getArgument(2);
            callback.onSuccess();
            return null;
        }).when(mockUsers).updateUserRole(eq(TEST_USER_ID), eq(NEW_ROLE_EMPLOYER), any(Users.RoleUpdateCallback.class));

        // Create intent for employee switching to employer
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ConfirmActivity.class);
        intent.putExtra("current_role", CURRENT_ROLE_EMPLOYEE);
        intent.putExtra("new_role", NEW_ROLE_EMPLOYER);
        intent.putExtra("current_email", TEST_EMAIL);
        intent.putExtra("user_id", TEST_USER_ID);

        // Launch ConfirmActivity and inject mock
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> activity.setUsersDb(mockUsers));

        // Enter correct email and confirm
        onView(withId(R.id.roleInputEmail))
                .perform(typeText(TEST_EMAIL), closeSoftKeyboard());
        onView(withId(R.id.roleConfirmButton))
                .perform(click());

        // Verify navigation to EmployerDashboardActivity
        intended(hasComponent(EmployerDashboardActivity.class.getName()));

        scenario.close();
        release();
    }




}
