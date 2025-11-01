package com.example.quick_cash.job_search;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.Models.Job;
import com.example.quick_cash.R;
import com.example.quick_cash.Utils.JobAdapter;
import com.example.quick_cash.Utils.JobSearchHandler;
import com.example.quick_cash.Utils.JobsCRUD;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JobSearchActivity extends AppCompatActivity {

    EditText userSearch;
    TextView resultsHeader;
    ListView resultsView;
    Button searchBtn;
    Spinner categorySelector;

    JobsCRUD jobsCRUD;

    JobSearchHandler jobSearcher;
    private JobAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_job_list);
        initUI();
        jobsCRUD = new JobsCRUD(FirebaseDatabase.getInstance());
        jobsCRUD.getJobs(callbackJobs -> {
            Log.d("JobSearchOut", "Jobs found: " + callbackJobs.toArray().length);
            jobSearcher = new JobSearchHandler(callbackJobs);
            loadJobs("", "");
            initListeners();
        });



    }

    protected void initUI() {
        this.userSearch = findViewById(R.id.userSearch);
        this.searchBtn = findViewById(R.id.searchBtn);
        this.resultsHeader = findViewById(R.id.textViewResHead);
        this.resultsView = findViewById(R.id.resultsView);
        this.categorySelector = findViewById(R.id.catSelect);
        ArrayList<String> categories = new ArrayList<String>(
                Arrays.asList("Category", "Finance", "Tech", "Education", "Health", "Construction", "AI")
        );
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                R.layout.category_item, // use your custom layout
                categories
        );
        adapter.setDropDownViewResource(R.layout.category_item);
        categorySelector.setAdapter(adapter);
    }

    protected void initListeners() {
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadJobs(userSearch.getText().toString().trim(),
                        categorySelector.getSelectedItem().toString());
            }
        });
    }

    protected void loadJobs(String search, String category) {
        ArrayList<Job> jobsToLoad = jobSearcher.getAllJobs(search, category);
        displayJobs(jobsToLoad);
    }

    protected void displayJobs(List<Job> jobs) {
        if (jobs.isEmpty()) {
            resultsView.setVisibility(View.GONE);
            resultsHeader.setText(R.string.NO_RESULT);
        } else {
            resultsHeader.setText(R.string.RESULT);
            resultsView.setVisibility(View.VISIBLE);

            adapter = new JobAdapter(this, R.layout.search_results_item, new ArrayList<>(jobs));
            resultsView.setAdapter(adapter);


            adapter.notifyDataSetChanged();
        }
    }
}
