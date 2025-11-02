package com.example.quick_cash.switch_role;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.quick_cash.FirebaseCRUD.Users;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UsersTest {

    @Mock
    private FirebaseDatabase mockDatabase;

    @Mock
    private DatabaseReference mockUsersRef;

    @Mock
    private DatabaseReference mockUserIdRef;

    @Mock
    private DatabaseReference mockTypeRef;

    @Mock
    private Task<Void> mockTask;

    private Users users;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up Firebase mock chain
        when(mockDatabase.getReference("users")).thenReturn(mockUsersRef);
        when(mockUsersRef.child(any(String.class))).thenReturn(mockUserIdRef);
        when(mockUserIdRef.child("type")).thenReturn(mockTypeRef);
        when(mockTypeRef.setValue(any(String.class))).thenReturn(mockTask);

        users = new Users(mockDatabase);
    }

    @Test
    public void testUpdateUserRoleFromEmployerToEmployee() {
        // Arrange
        String userId = "user123";
        String newRole = "employee";
        Users.RoleUpdateCallback callback = mock(Users.RoleUpdateCallback.class);

        // Mock successful Firebase update
        doAnswer(invocation -> {
            OnSuccessListener<Void> listener = invocation.getArgument(0);
            listener.onSuccess(null);
            return mockTask;
        }).when(mockTask).addOnSuccessListener(any(OnSuccessListener.class));

        doAnswer(invocation -> {
            return mockTask;
        }).when(mockTask).addOnFailureListener(any(OnFailureListener.class));

        // Act
        users.updateUserRole(userId, newRole, callback);

        // Assert
        verify(mockUsersRef).child(userId);
        verify(mockUserIdRef).child("type");
        verify(mockTypeRef).setValue("employee");
        verify(callback).onSuccess();
    }

    @Test
    public void testUpdateUserRoleFromEmployeeToEmployer() {
        // Arrange
        String userId = "user456";
        String newRole = "employer";
        Users.RoleUpdateCallback callback = mock(Users.RoleUpdateCallback.class);

        // Mock successful Firebase update
        doAnswer(invocation -> {
            OnSuccessListener<Void> listener = invocation.getArgument(0);
            listener.onSuccess(null);
            return mockTask;
        }).when(mockTask).addOnSuccessListener(any(OnSuccessListener.class));

        doAnswer(invocation -> {
            return mockTask;
        }).when(mockTask).addOnFailureListener(any(OnFailureListener.class));

        // Act
        users.updateUserRole(userId, newRole, callback);

        // Assert
        verify(mockUsersRef).child(userId);
        verify(mockUserIdRef).child("type");
        verify(mockTypeRef).setValue("employer");
        verify(callback).onSuccess();
    }

    @Test
    public void testUpdateUserRoleUpdatesFirebase() {
        // Arrange
        String userId = "user789";
        String newRole = "employee";
        Users.RoleUpdateCallback callback = mock(Users.RoleUpdateCallback.class);

        // Mock successful Firebase update
        doAnswer(invocation -> {
            OnSuccessListener<Void> listener = invocation.getArgument(0);
            listener.onSuccess(null);
            return mockTask;
        }).when(mockTask).addOnSuccessListener(any(OnSuccessListener.class));

        doAnswer(invocation -> {
            return mockTask;
        }).when(mockTask).addOnFailureListener(any(OnFailureListener.class));

        // Act
        users.updateUserRole(userId, newRole, callback);

        // Assert - Verify Firebase database interaction
        verify(mockDatabase).getReference("users");
        verify(mockUsersRef).child(userId);
        verify(mockUserIdRef).child("type");
        verify(mockTypeRef).setValue(newRole);
        verify(mockTask).addOnSuccessListener(any(OnSuccessListener.class));
        verify(mockTask).addOnFailureListener(any(OnFailureListener.class));
    }
}
