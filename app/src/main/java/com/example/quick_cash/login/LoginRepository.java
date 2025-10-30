package com.example.quick_cash.login;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public interface LoginRepository {
    Task<FirebaseUser> signIn(String email, String password);
}