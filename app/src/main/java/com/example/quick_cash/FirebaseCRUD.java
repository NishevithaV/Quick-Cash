package com.example.quick_cash;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Map;

public class FirebaseCRUD {
    private final FirebaseDatabase database;
    private DatabaseReference jobListRef;
    private DatabaseReference usersRef;


    public FirebaseCRUD(FirebaseDatabase database) {
        this.database = database;
        this.initializeDatabaseRefs();
        this.initializeRefListeners();
    }

    private void initializeDatabaseRefs() {
        this.jobListRef = this.database.getReference("job_listings");
        this.usersRef = this.database.getReference("users");

    }

    private void initializeRefListeners() {
        this.setJobListListener();
        this.setUsersListener();
    }

    private void setJobListListener(){

    }

    private void setUsersListener(){

    }

    public boolean postJob(){
        return false;
    }

    public boolean postUsers(){
        return false;
    }

    public void getJob(){
    }

    public void getUser(){
    }
}
