package com.example.quick_cash.views.employer;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;
import com.example.quick_cash.models.Job;
import com.example.quick_cash.utils.ApplicationAdapter;
import com.example.quick_cash.utils.ApplicationsFilterHandler;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.example.quick_cash.utils.JobAdapter;
import com.example.quick_cash.utils.JobIdMapper;
import com.example.quick_cash.utils.UserIdMapper;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ApplicationsActivity extends AppCompatActivity {

    Spinner statusFilter;
    ListView appsResultsView;
    TextView appsResHead;
    Applications appsCRUD;

    private String selectedStatus;
    public String toastMsg;
    private ArrayList<Application> displayedApps;
    private ApplicationsFilterHandler filterHandler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_applications);
        selectedStatus = "pending";
        initUI();
        appsCRUD = new Applications(FirebaseDatabase.getInstance());
        appsCRUD.getApplications(new Applications.AppsCallback() {
            @Override
            public void onCallback(ArrayList<Application> apps) {
                filterHandler = new ApplicationsFilterHandler(apps);
                loadApps(selectedStatus);
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
                loadApps(selectedStatus);
            }
        });
    }

    private void initUI() {
        this.statusFilter = findViewById(R.id.statusFilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.statuses,
                R.layout.status_spinner_item
        );
        adapter.setDropDownViewResource(R.layout.status_spinner_item);
        statusFilter.setAdapter(adapter);
        this.appsResultsView = findViewById(R.id.appsResultsView);
        this.appsResHead = findViewById(R.id.appsResHead);
    }

    private void initListeners() {

        appsResultsView.setOnItemClickListener((parent, view, position, id) -> {
            Application selectedApp = displayedApps.get(position);
            if (selectedApp.getStatus().equalsIgnoreCase("pending")) {
                Intent intent = new Intent(ApplicationsActivity.this, ApplicationReviewActivity.class);
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
            } else {
            }
        });
    }

    private void loadApps(String status) {
        ArrayList<Application> appsToLoad = filterHandler.getApps(status);
        displayApps(appsToLoad);
    }

    private void displayApps(ArrayList<Application> apps) {
        displayedApps.clear();
        displayedApps.addAll(apps);

        if (displayedApps.isEmpty()) {
            appsResultsView.setVisibility(View.GONE);
            appsResHead.setText(R.string.NO_RESULT);
        } else {
            appsResultsView.setVisibility(View.VISIBLE);
            appsResHead.setText(R.string.RESULT);

            ApplicationAdapter adapter = new ApplicationAdapter(this, R.layout.applications_item, new ArrayList<>(apps));
            appsResultsView.setAdapter(adapter);

            adapter.notifyDataSetChanged();
        }
    }
}
