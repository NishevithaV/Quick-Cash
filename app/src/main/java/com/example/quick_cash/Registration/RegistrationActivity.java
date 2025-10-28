package com.example.quick_cash.Registration;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;


public class RegistrationActivity extends AppCompatActivity {

    private String name;
    private String email;
    private String password;
    private String userType;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        this.setContentView(R.layout.activity_registration);
    }


    public boolean ValidUser(String userType){
        return true;
    }
    public boolean ValidName(String name){
        return true;
    }
    public boolean ValidEmail(String email){
        return true;
    }
    public boolean ValidPassword(String password){
        return true;

    }
}