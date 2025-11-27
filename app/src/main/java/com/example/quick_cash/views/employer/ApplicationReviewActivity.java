package com.example.quick_cash.views.employer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.google.firebase.database.FirebaseDatabase;

public class ApplicationReviewActivity extends AppCompatActivity {

    Button declineBtn;
    Button acceptBtn;

    TextView applicantNameAppRev;
    TextView jobTitleAppRev;
    TextView statusAppRev;
    TextView cvrLtrAppRev;
    public String toastMsg;

    private String appId;
    private String letter;
    private String applicantName;
    private String status;
    private String jobTitle;
    private Applications appsCRUD;

    /**
     * Oncreate function
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_application_review);
        appsCRUD = new Applications(FirebaseDatabase.getInstance());
        Intent intent = getIntent();
        letter = intent.getStringExtra("letter");
        applicantName = intent.getStringExtra("applicantName");
        status = intent.getStringExtra("status");
        jobTitle = intent.getStringExtra("jobTitle");
        appId = intent.getStringExtra("appId");
        initUI();
        initListeners();
    }

    private void initUI() {
        applicantNameAppRev = findViewById(R.id.applicantNameAppRev);
        applicantNameAppRev.setText(applicantName);
        jobTitleAppRev = findViewById(R.id.jobTitleAppRev);
        jobTitleAppRev.setText(jobTitle);
        statusAppRev = findViewById(R.id.statusAppRev);
        statusAppRev.setText(status);
        cvrLtrAppRev = findViewById(R.id.cvrLtrAppRev);
        cvrLtrAppRev.setText(letter);
        declineBtn = findViewById(R.id.declineBtn);
        acceptBtn = findViewById(R.id.acceptBtn);
    }

    private void initListeners() {

    }

}
