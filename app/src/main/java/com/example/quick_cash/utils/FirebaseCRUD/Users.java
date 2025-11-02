package com.example.quick_cash.utils.FirebaseCRUD;

import androidx.annotation.NonNull;

import com.example.quick_cash.models.User;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import java.util.function.Consumer;

public class Users {
    private final FirebaseAuth auth;
    private final FirebaseDatabase database;
    private DatabaseReference usersRef;

    /**
     * Instantiates a new Users.
     *
     * @param database the database
     */
    public Users(FirebaseDatabase database) {
        auth = FirebaseAuth.getInstance();
        this.database = database;
        this.initializeDatabaseRefs();
    }

    private void initializeDatabaseRefs() {
        this.usersRef = this.database.getReference("users");
    }

    /**
     * Create user.
     *
     * @param name      the name
     * @param email     the email
     * @param password  the password
     * @param onSuccess the on success
     * @param onFailure the on failure
     */
// Create user in Firebase Authentication
    public void createUser(String name, String email, String password, Consumer<AuthResult> onSuccess, Consumer<Exception> onFailure) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(onSuccess::accept)
                .addOnFailureListener(onFailure::accept);
    }

    /**
     * Save user data.
     *
     * @param userId   the user id
     * @param userData the user data
     */
//Save in Realtime DB
    public void saveUserData(String userId, Map<String, Object> userData) {
        usersRef.child(userId).setValue(userData);
    }

    /**
     * The interface Users callback.
     */
    public interface UsersCallback {
        /**
         * On callback.
         *
         * @param user the user
         */
        void onCallback(User user);
    }

    /**
     * Get user.
     *
     * @param userID   the user id
     * @param callback the callback
     */
    public void getUser(String userID, UsersCallback callback){
        final User[] userArr = new User[1];
        usersRef.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userArr[0] = new User(
                        String.valueOf(snapshot.child("userType").getValue(String.class)),
                        String.valueOf(snapshot.child("email").getValue(String.class)),
                        String.valueOf(snapshot.child("name").getValue(String.class)),
                        String.valueOf(snapshot.child("userId").getValue(String.class))
                );
                callback.onCallback(userArr[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Do nothing
            }
        });
    }

    /**
     * The interface Role update callback.
     */
// Interface for role update callback
    public interface RoleUpdateCallback {
        /**
         * On success.
         */
        void onSuccess();

        /**
         * On failure.
         *
         * @param error the error
         */
        void onFailure(String error);
    }

    /**
     * Update user role.
     *
     * @param userId   the user id
     * @param newRole  the new role
     * @param callback the callback
     */
// Method to update user role in Firebase
    public void updateUserRole(String userId, String newRole, RoleUpdateCallback callback) {
        usersRef.child(userId).child("userType").setValue(newRole)
                .addOnSuccessListener(aVoid -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onFailure(e.getMessage()));
    }
}
