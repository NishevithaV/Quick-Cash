package com.example.quick_cash.utils;


import com.example.quick_cash.utils.FirebaseCRUD.Users;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The type User id mapper.
 */
public final class UserIdMapper {

    private static final Users usersCRUD = new Users(FirebaseDatabase.getInstance());

    private UserIdMapper() {} // Prevent instantiation

    /**
     * Get user name given user id
     *
     * @param userId   id to get name for
     * @param callback interface with method to handle user name
     */
    public static void getName(String userId, UserNameCallback callback) {
        usersCRUD.getUser(userId, user -> {
            String name = user.getName();
            callback.onNameLoaded(name != null && !name.equals("null") ? name : "Test User");
        });
    }

    /**
     * Get user role given user id
     *
     * @param userId   id to get role for
     * @param callback interface with method to handle user role
     */
    public static void getRole(String userId, UserNameCallback callback) {
        usersCRUD.getUser(userId, user -> {
            String role = user.getType();
            callback.onNameLoaded(role != null && !role.equals("null") ? role : "Test User Role");
        });
    }

    /**
     * Interface to be used as callback with getName()
     */
    public interface UserNameCallback {
        /**
         * Run On name loaded. To be implemented
         *
         * @param name the name
         */
        void onNameLoaded(String name);
    }
}
