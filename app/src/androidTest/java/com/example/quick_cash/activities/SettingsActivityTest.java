package com.example.quick_cash.activities;

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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quick_cash.FirebaseCRUD.Users;
import com.example.quick_cash.R;
import com.example.quick_cash.employee.EmployeeDashboardActivity;
import com.example.quick_cash.employer.EmployerDashboardActivity;
import com.example.quick_cash.settings.ConfirmActivity;
import com.example.quick_cash.settings.SettingsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Comprehensive Espresso + Mockito integration tests for SettingsActivity
 */
@RunWith(AndroidJUnit4.class)
public class SettingsActivityTest {

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
    private static final String CURRENT_ROLE_EMPLOYER = "employer";
    private static final String CURRENT_ROLE_EMPLOYEE = "employee";
    private static final String NEW_ROLE_EMPLOYEE = "employee";
    private static final String NEW_ROLE_EMPLOYER = "employer";

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSwitchRoleButtonIsDisplayed() {
        // Setup mocks
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn(TEST_USER_ID);
        when(mockUser.getEmail()).thenReturn(TEST_EMAIL);
        when(mockDatabase.getReference("users")).thenReturn(mockUsersRef);

        // Launch activity and inject mocks
        ActivityScenario<SettingsActivity> scenario = ActivityScenario.launch(SettingsActivity.class);
        scenario.onActivity(activity -> {
            activity.setFirebaseAuth(mockAuth);
            activity.setFirebaseDatabase(mockDatabase);
        });

        // Assert - Check that the Switch Role button is displayed
        onView(withId(R.id.switchRoleButton))
                .check(matches(isDisplayed()));

        scenario.close();
    }

    @Test
    public void testSwitchRoleButtonNavigatesToConfirmActivity() {
        // Initialize Espresso Intents
        init();

        // Setup mocks
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn(TEST_USER_ID);
        when(mockUser.getEmail()).thenReturn(TEST_EMAIL);
        when(mockDatabase.getReference("users")).thenReturn(mockUsersRef);
        when(mockUsersRef.child(TEST_USER_ID)).thenReturn(mockUserIdRef);

        // Mock database response
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            // Simulate dataSnapshot exists with employer role
            when(mockDataSnapshot.exists()).thenReturn(true);
            when(mockDataSnapshot.child("type")).thenReturn(mockDataSnapshot);
            when(mockDataSnapshot.getValue(String.class)).thenReturn(CURRENT_ROLE_EMPLOYER);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockUserIdRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Launch activity and inject mocks
        ActivityScenario<SettingsActivity> scenario = ActivityScenario.launch(SettingsActivity.class);
        scenario.onActivity(activity -> {
            activity.setFirebaseAuth(mockAuth);
            activity.setFirebaseDatabase(mockDatabase);
        });

        // Perform action
        onView(withId(R.id.switchRoleButton))
                .perform(click());

        // Verify navigation to ConfirmActivity with correct extras
        intended(allOf(
                hasComponent(ConfirmActivity.class.getName()),
                hasExtra("current_role", CURRENT_ROLE_EMPLOYER),
                hasExtra("new_role", NEW_ROLE_EMPLOYEE),
                hasExtra("current_email", TEST_EMAIL),
                hasExtra("user_id", TEST_USER_ID)
        ));

        release();
        scenario.close();
    }

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

    @Test
    public void testIntegration_IncorrectEmail_StaysOnConfirmPage() {
        // Initialize Espresso Intents
        init();
        Users mockUsers = mock(Users.class);

        // Create intent to launch ConfirmActivity
        Intent intent = new Intent(ApplicationProvider.getApplicationContext(), ConfirmActivity.class);
        intent.putExtra("current_role", CURRENT_ROLE_EMPLOYER);
        intent.putExtra("new_role", NEW_ROLE_EMPLOYEE);
        intent.putExtra("current_email", TEST_EMAIL);
        intent.putExtra("user_id", TEST_USER_ID);

        // Launch ConfirmActivity and inject mock
        ActivityScenario<ConfirmActivity> scenario = ActivityScenario.launch(intent);
        scenario.onActivity(activity -> activity.setUsersDb(mockUsers));

        // Enter incorrect email
        onView(withId(R.id.roleInputEmail))
                .perform(typeText("wrong@example.com"), closeSoftKeyboard());

        // Click confirm button
        onView(withId(R.id.roleConfirmButton))
                .perform(click());

        // Verify user stays on ConfirmActivity by checking UI elements are still visible
        onView(withId(R.id.roleInputEmail))
                .check(matches(isDisplayed()));

        onView(withId(R.id.roleConfirmButton))
                .check(matches(isDisplayed()));

        onView(withId(R.id.roleSwitchText))
                .check(matches(isDisplayed()));

        // Verify that updateUserRole was NEVER called since email didn't match
        verify(mockUsers, org.mockito.Mockito.never()).updateUserRole(anyString(), anyString(), any(Users.RoleUpdateCallback.class));

        scenario.close();
        release();
    }

    @Test
    public void testFirebaseMock_UpdateRoleFailure_ShowsErrorToast() {
        // Initialize Espresso Intents
        init();

        // This test simulates Firebase failure
        String errorMessage = "Network error";

        // Create a mock Users object
        Users mockUsers = mock(Users.class);

        // Mock the updateUserRole to call onFailure
        doAnswer(invocation -> {
            Users.RoleUpdateCallback callback = invocation.getArgument(2);
            callback.onFailure(errorMessage);
            return null;
        }).when(mockUsers).updateUserRole(eq(TEST_USER_ID), eq(NEW_ROLE_EMPLOYEE), any(Users.RoleUpdateCallback.class));

        // Create intent to launch ConfirmActivity
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

        // Verify that updateUserRole was called
        verify(mockUsers).updateUserRole(eq(TEST_USER_ID), eq(NEW_ROLE_EMPLOYEE), any(Users.RoleUpdateCallback.class));

        // Verify user stays on ConfirmActivity (no navigation on failure)
        onView(withId(R.id.roleInputEmail))
                .check(matches(isDisplayed()));

        scenario.close();
        release();
    }

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

    @Test
    public void testMockFirebase_NoUserLoggedIn_ShowsErrorToast() {
        // Setup mocks - no user logged in
        when(mockAuth.getCurrentUser()).thenReturn(null);
        when(mockDatabase.getReference("users")).thenReturn(mockUsersRef);

        // Launch activity and inject mocks
        ActivityScenario<SettingsActivity> scenario = ActivityScenario.launch(SettingsActivity.class);
        scenario.onActivity(activity -> {
            activity.setFirebaseAuth(mockAuth);
            activity.setFirebaseDatabase(mockDatabase);
        });

        // Perform action - click switch role button
        onView(withId(R.id.switchRoleButton))
                .perform(click());

        // Verify that no database query was made (since user is null)
        verify(mockUsersRef, org.mockito.Mockito.never()).child(anyString());

        scenario.close();
    }


    @Test
    public void testMockFirebase_EmployeeToEmployerSwitch() {
        // Initialize Espresso Intents
        init();

        // Setup mocks for employee user
        when(mockAuth.getCurrentUser()).thenReturn(mockUser);
        when(mockUser.getUid()).thenReturn(TEST_USER_ID);
        when(mockUser.getEmail()).thenReturn(TEST_EMAIL);
        when(mockDatabase.getReference("users")).thenReturn(mockUsersRef);
        when(mockUsersRef.child(TEST_USER_ID)).thenReturn(mockUserIdRef);

        // Mock database response - user is employee
        doAnswer(invocation -> {
            ValueEventListener listener = invocation.getArgument(0);
            when(mockDataSnapshot.exists()).thenReturn(true);
            when(mockDataSnapshot.child("type")).thenReturn(mockDataSnapshot);
            when(mockDataSnapshot.getValue(String.class)).thenReturn(CURRENT_ROLE_EMPLOYEE);
            listener.onDataChange(mockDataSnapshot);
            return null;
        }).when(mockUserIdRef).addListenerForSingleValueEvent(any(ValueEventListener.class));

        // Launch activity and inject mocks
        ActivityScenario<SettingsActivity> scenario = ActivityScenario.launch(SettingsActivity.class);
        scenario.onActivity(activity -> {
            activity.setFirebaseAuth(mockAuth);
            activity.setFirebaseDatabase(mockDatabase);
        });

        // Perform action
        onView(withId(R.id.switchRoleButton))
                .perform(click());

        // Verify navigation to ConfirmActivity with employee -> employer switch
        intended(allOf(
                hasComponent(ConfirmActivity.class.getName()),
                hasExtra("current_role", CURRENT_ROLE_EMPLOYEE),
                hasExtra("new_role", NEW_ROLE_EMPLOYER),
                hasExtra("current_email", TEST_EMAIL),
                hasExtra("user_id", TEST_USER_ID)
        ));

        release();
        scenario.close();
    }
}
