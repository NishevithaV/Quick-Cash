package com.example.quick_cash.views.employer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.List;

/**
 * The type Applications activity.
 */
public class ApplicationsActivity extends AppCompatActivity {

    /**
     * The Status filter.
     */
    Spinner statusFilter;
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

    private String selectedStatus;
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
        selectedStatus = "pending";
        initUI();
        appsCRUD = new Applications(FirebaseDatabase.getInstance());
        appsCRUD.getApplications(apps -> {
            filterHandler = new ApplicationsFilterHandler(apps);
            loadApps(selectedStatus);
            initListeners();
        });
        displayedApps = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();
        appsCRUD.getApplications(apps -> {
            filterHandler = new ApplicationsFilterHandler(apps);
            loadApps(selectedStatus);
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
        statusFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedStatus = parent.getItemAtPosition(position).toString();
                loadApps(selectedStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        appsResultsView.setOnItemClickListener((parent, view, position, id) -> {
            Application selectedApp = displayedApps.get(position);
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
        });
    }

    private void loadApps(String status) {
        ArrayList<Application> appsToLoad = filterHandler.getAppsByStatus(status);
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

    /**
     * Sets displayed apps for test.
     *
     * @param apps the apps
     */
    public void setDisplayedAppsForTest(List<Application> apps) {
        displayedApps.clear();
        displayedApps.addAll(apps);
        displayApps(new ArrayList<>(apps));
    }
}
