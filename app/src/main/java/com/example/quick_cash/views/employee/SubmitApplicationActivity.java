package com.example.quick_cash.views.employee;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.SubmitApplicationHandler;
import com.example.quick_cash.views.switch_role.ConfirmActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SubmitApplicationActivity extends AppCompatActivity {
    Button submitBtn;
    EditText cvrLtrInput;
    private String jobID;
    SubmitApplicationHandler submitHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_submit_application);
        Intent intent = getIntent();
        jobID = intent.getStringExtra("jobID");
        submitHandler = new SubmitApplicationHandler();
        initUI();
        initListeners();
    }

    private void initUI() {
        cvrLtrInput = findViewById(R.id.cvrLtrInput);
        submitBtn = findViewById(R.id.submitLtrBtn);
    }

    private void initListeners() {

    }
}
