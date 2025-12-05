package com.example.quick_cash.views.employee;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.R;
import com.example.quick_cash.models.Application;
import com.example.quick_cash.utils.EmployeeApplicationAdapter;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class EmployeeApplicationsActivity extends AppCompatActivity {

    ListView appsListView;
    Applications appsCRUD;
    public String toastMsg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_employee_application_tracking);
        appsCRUD = new Applications(FirebaseDatabase.getInstance());
        initUI();
        if (!getIntent().getBooleanExtra("isTest", false)) loadApps();
    }

    private void initUI() {
        appsListView = findViewById(R.id.recycler_applications);
    }

    private void loadApps() {
        appsCRUD.getApplications(new Applications.AppsCallback() {
            @Override
            public void onCallback(ArrayList<Application> apps) {
                displayApps(apps);
            }
        });
    }
    private void displayApps(ArrayList<Application> apps) {
        EmployeeApplicationAdapter adapter = new EmployeeApplicationAdapter(
                this,
                R.layout.item_application_card,
                apps,
                new EmployeeApplicationAdapter.OnItemActionListener() {
                    @Override
                    public void onMarkCompleted(Application app, int position) {
                        app.setStatus("completed");
                        apps.set(position, app);
                        appsCRUD.updateStatus(app.getId(), "completed");
                        toastMsg = getString(R.string.MARKED_COMPLETED);
                        Toast.makeText(EmployeeApplicationsActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                        displayApps(apps);
                    }

                    @Override
                    public void onCardClicked(Application app, int position) {
                        // Do nothing here
                    }
                }
        );
        appsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void displayAppsForTest(ArrayList<Application> apps) {
        EmployeeApplicationAdapter adapter = new EmployeeApplicationAdapter(
                this,
                R.layout.item_application_card,
                apps,
                new EmployeeApplicationAdapter.OnItemActionListener() {
                    @Override
                    public void onMarkCompleted(Application app, int position) {
                        app.setStatus("completed");
                        apps.set(position, app);
                        toastMsg = getString(R.string.MARKED_COMPLETED);
                        Toast.makeText(EmployeeApplicationsActivity.this, toastMsg, Toast.LENGTH_SHORT).show();
                        displayAppsForTest(apps);
                    }

                    @Override
                    public void onCardClicked(Application app, int position) {
                        // Do nothing here
                    }
                }
        );
        appsListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
