package com.example.quick_cash.views.employer;

import static android.view.View.GONE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;
import com.example.quick_cash.utils.ApplicationAdapter;
import com.example.quick_cash.utils.ApplicationsFilterHandler;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.example.quick_cash.utils.JobIdMapper;
import com.example.quick_cash.utils.UserIdMapper;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * The type Job applications activity.
 */
public class JobApplicationsActivity extends AppCompatActivity {

    /**
     * The Status frame.
     */
    FrameLayout statusFrame;
    /**
     * The Heading.
     */
    TextView heading;
    /**
     * The Apps results view.
     */
    ListView appsResultsView;
    /**
     * The Apps res head.
     */
    TextView appsResHead;
    /**
     * The Apps crud.
     */
    Applications appsCRUD;

    private String selectedJobId;
    private String selectedJobTitle;
    /**
     * The Toast msg.
     */
    public String toastMsg;
    private ArrayList<Application> displayedApps;
    private ApplicationsFilterHandler filterHandler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_applications);
        selectedJobId = getIntent().getStringExtra("jobID");
        selectedJobTitle = getIntent().getStringExtra("jobTitle");
        initUI();
        appsCRUD = new Applications(FirebaseDatabase.getInstance());
        appsCRUD.getApplications(new Applications.AppsCallback() {
            @Override
            public void onCallback(ArrayList<Application> apps) {
                filterHandler = new ApplicationsFilterHandler(apps);
                loadApps(selectedJobId);
                initListeners();
            }
        });
        displayedApps = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appsCRUD.getApplications(new Applications.AppsCallback() {
            @Override
            public void onCallback(ArrayList<Application> apps) {
                filterHandler = new ApplicationsFilterHandler(apps);
                loadApps(selectedJobId);
            }
        });
    }

    private void initUI() {
        this.statusFrame = findViewById(R.id.statusFilterFrame);
        statusFrame.setVisibility(GONE);
        this.appsResultsView = findViewById(R.id.appsResultsView);
        this.appsResHead = findViewById(R.id.appsResHead);
        this.heading = findViewById(R.id.viewAppsHeading);
        String jobHeader = selectedJobTitle+" Applications";
        heading.setText(jobHeader);
    }

    private void initListeners() {
        appsResultsView.setOnItemClickListener((parent, view, position, id) -> {
            Application selectedApp = displayedApps.get(position);
            Intent intent = new Intent(JobApplicationsActivity.this, ApplicationReviewActivity.class);
            UserIdMapper.getName(selectedApp.getApplicantId(), name -> {
                intent.putExtra("applicantName", name);
            });
            intent.putExtra("letter", selectedApp.getLetter());
            intent.putExtra("status", selectedApp.getStatus());
            intent.putExtra("appId", selectedApp.getId());
            JobIdMapper.getTitle(selectedApp.getJobId(), title -> {
                intent.putExtra("jobTitle", title);
                startActivity(intent);
            });
        });
    }

    private void loadApps(String search) {
        ArrayList<Application> appsToLoad = filterHandler.getAppsByJob(search);
        displayApps(appsToLoad);
    }

    private void displayApps(ArrayList<Application> apps) {
        displayedApps.clear();
        displayedApps.addAll(apps);

        if (displayedApps.isEmpty()) {
            appsResultsView.setVisibility(GONE);
            appsResHead.setText(R.string.NO_RESULT);
        } else {
            appsResultsView.setVisibility(View.VISIBLE);
            appsResHead.setText(R.string.RESULT);

            ApplicationAdapter adapter = new ApplicationAdapter(this, R.layout.applications_item, new ArrayList<>(apps));
            appsResultsView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
    }

    /**
     * Sets displayed apps for test.
     *
     * @param apps the apps
     */
    public void setDisplayedAppsForTest(ArrayList<Application> apps) {
        displayedApps.clear();
        displayedApps.addAll(apps);
        displayApps(apps);
    }
}
