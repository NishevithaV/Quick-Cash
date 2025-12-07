package com.example.quick_cash.views.employer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.google.firebase.database.FirebaseDatabase;

/**
 * The type Application review activity.
 */
public class ApplicationReviewActivity extends AppCompatActivity {

    /**
     * The Decline btn.
     */
    Button declineBtn;
    /**
     * The Accept btn.
     */
    Button acceptBtn;

    /**
     * The Applicant name app rev.
     */
    TextView applicantNameAppRev;
    /**
     * The Job title app rev.
     */
    TextView jobTitleAppRev;
    /**
     * The Status app rev.
     */
    TextView statusAppRev;
    /**
     * The Cvr ltr app rev.
     */
    TextView cvrLtrAppRev;
    /**
     * The Toast msg.
     */
    public String toastMsg;

    private boolean accepted;

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
        if(status.equalsIgnoreCase("completed")){
            declineBtn.setVisibility(View.GONE);
            acceptBtn.setText("Approve and Pay");
            accepted = true;
        }
        if (status.equalsIgnoreCase("accepted")) {
            statusAppRev.setTextColor(Color.GREEN);
            declineBtn.setVisibility(View.GONE);
            acceptBtn.setVisibility(View.GONE);
        } else if (status.equalsIgnoreCase("declined")) {
            statusAppRev.setTextColor(Color.RED);
            declineBtn.setVisibility(View.GONE);
            acceptBtn.setVisibility(View.GONE);
        }else if(status.equalsIgnoreCase("paid")){
            declineBtn.setVisibility(View.GONE);
            acceptBtn.setVisibility(View.GONE);
        }

    }

    private void initListeners() {
        declineBtn.setOnClickListener(v -> {
            update("declined");
        });

        acceptBtn.setOnClickListener(v -> {
            if (!accepted) {
                accepted = true;
                update("accepted");
            } else {
                Intent intent = new Intent(ApplicationReviewActivity.this, EmployerPaymentActivity.class);
                intent.putExtra("jobApplicationId", appId);
                startActivityForResult(intent, 5000);

            }
        });
    }

    private void update(String status) {
        this.status = status;
        statusAppRev.setText(status);

        if (status.equalsIgnoreCase("declined")) {
            statusAppRev.setTextColor(Color.RED);
            declineBtn.setVisibility(View.GONE);
            acceptBtn.setVisibility(View.GONE);
        } else if (status.equalsIgnoreCase("accepted")) {
            statusAppRev.setTextColor(Color.GREEN);
            declineBtn.setVisibility(View.GONE);
            acceptBtn.setVisibility(View.GONE);
        }



        appsCRUD.updateStatus(appId, status);
        toastMsg = "Application successfully "+status;
        Toast.makeText(ApplicationReviewActivity.this, toastMsg, Toast.LENGTH_SHORT).show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 5000 && resultCode == RESULT_OK) {
            if (data != null && data.getBooleanExtra("paymentApproved", false)) {

                status = "paid";
                statusAppRev.setText("paid");

                acceptBtn.setVisibility(View.GONE);
                declineBtn.setVisibility(View.GONE);

                Toast.makeText(this, "Payment Completed. Application Approved!", Toast.LENGTH_LONG).show();
            }
        }
    }

}
