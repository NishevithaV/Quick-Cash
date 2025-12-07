package com.example.quick_cash.views.employer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.quick_cash.controllers.JobPostingValidator;
import com.example.quick_cash.utils.AccessTokenListener;
import com.example.quick_cash.utils.FirebaseCRUD.Jobs;
import com.example.quick_cash.models.Job;
import com.example.quick_cash.R;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PostFormActivity extends AppCompatActivity implements View.OnClickListener {

    //path
    private static final String CREDENTIALS_FILE_PATH = "key.json";

    //provided by google - for sending the notification

    //new endpoint
    private static final String PUSH_NOTIFICATION_ENDPOINT ="https://fcm.googleapis.com/v1/projects/quickcash-72ee9/messages:send";

    //provided by volley library to make a network request
    private RequestQueue requestQueue;

    private String jobId;

    Jobs jobsCRUD;
    JobPostingValidator validator;
    EditText jobName;
    EditText jobLocation;

    Spinner jobCategorySpinner;
    EditText applicationDeadline;
    EditText jobDescription;
    TextView result;

    private FirebaseAuth auth;

    /**
     * Overriden onCreate function to start activity, initialize UI, properties, and set listeners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_post_form);
        auth = FirebaseAuth.getInstance();
        this.validator = new JobPostingValidator();
        if (jobsCRUD == null) {
            this.jobsCRUD = new Jobs(getFirebaseDatabase());
        }
        initUIElements();
        //adding multiple network request to a queue, FIFO based, running it separate threads, cannot run network request on the main thread in android
        //volley creates a separate thread for the network request
        requestQueue = Volley.newRequestQueue(this);

        //jobs is the topic name,subscribing to the jobs notification tray


        this.loadJobCategorySpinner();
        this.setupPostJobButton();
    }

    /**
     * Gets firebase database.
     *
     * @return the firebase database
     */
    protected FirebaseDatabase getFirebaseDatabase() {
        return FirebaseDatabase.getInstance("https://quickcash-72ee9-default-rtdb.firebaseio.com/");
    }

    /**
     * Load job category spinner.
     */
    protected void loadJobCategorySpinner() {
        jobCategorySpinner = findViewById(R.id.JobCategorySpinner);
        List<String> types = new ArrayList<>();
        types.add("Select job category");
        types.add("IT");
        types.add("Finance");
        types.add("Healthcare");
        types.add("AI");
        types.add("engineering");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jobCategorySpinner.setAdapter(typeAdapter);
    }

    /**
     * Sets post job button.
     */
    protected void setupPostJobButton() {
        Button postJobButton = findViewById(R.id.PostJobButton);
        postJobButton.setOnClickListener(this);
    }

    /**
     * Init ui elements.
     */
    protected void initUIElements() {
        jobName = findViewById(R.id.JobTitleField);
        jobLocation = findViewById(R.id.JobLocationField);
        jobCategorySpinner = findViewById(R.id.JobCategorySpinner);
        applicationDeadline = findViewById(R.id.ApplicationDeadlineField);
        jobDescription = findViewById(R.id.JobDescriptionField);
        result = findViewById(R.id.Result);
    }

    private void showToast(String message) {
        Toast.makeText(PostFormActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Validate and move to Employer dashboard when view is clicked
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(View view) {
        String userId = getCurrentUserID();
        String enteredJobTitle = jobName.getText().toString().trim();
        String enteredJobCategory = jobCategorySpinner.getSelectedItem().toString();
        String enteredJobLocation = jobLocation.getText().toString().trim();
        String enteredJobDeadline = applicationDeadline.getText().toString().trim();
        String enteredJobDescription = jobDescription.getText().toString().trim();

        String errorMessage = "";
        if (validator.checkEmptyJobTitle(enteredJobTitle)) {
            errorMessage = getResources().getString(R.string.EMPTY_JOB_TITLE);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (!validator.checkValidJobCategory(enteredJobCategory)) {
            errorMessage = getResources().getString(R.string.INVALID_JOB_CATEGORY);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (validator.checkEmptyJobLocation(enteredJobLocation)) {
            errorMessage = getResources().getString(R.string.EMPTY_LOCATION);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (validator.checkEmptyApplicationDeadline(enteredJobDeadline)) {
            errorMessage = getResources().getString(R.string.EMPTY_DEADLINE);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (!validator.checkValidApplicationDeadline(enteredJobDeadline)) {
            errorMessage = getResources().getString(R.string.INVALID_APPLICATION_DEADLINE);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        if (validator.checkEmptyJobDescription(enteredJobDescription)) {
            errorMessage = getResources().getString(R.string.EMPTY_DESCRIPTION);
            result.setText(errorMessage);
            showToast(errorMessage);
            return;
        }

        // Geocode the location to get coordinates
        Geocoder geocoder = new Geocoder(this);
        try {
            List<Address> addresses = geocoder.getFromLocationName(enteredJobLocation, 1);
            if (addresses == null || addresses.isEmpty()) {
                errorMessage = getResources().getString(R.string.INVALID_LOCATION);
                result.setText(errorMessage);
                showToast(errorMessage);
                return;
            }

            Address address = addresses.get(0);
            double latitude = address.getLatitude();
            double longitude = address.getLongitude();

            Job job = new Job(
                    enteredJobTitle,
                    enteredJobCategory,
                    enteredJobLocation,
                    enteredJobDeadline,
                    enteredJobDescription,
                    userId,
                    latitude,
                    longitude);


            Intent intent = getIntent();
            boolean isTest = intent.getBooleanExtra("isTest", false);

            if (!isTest) jobId = jobsCRUD.postJob(job);
            else jobId = "TestJobId";

            if (jobId == null) {
                showToast("Job posting failed");
                result.setText(getResources().getString(R.string.EMPTY_STRING));
                return;
            }

            showToast("Job posted successfully");
            result.setText(getResources().getString(R.string.EMPTY_STRING));


            // Check nearby
            if (enteredJobLocation.contains("Halifax") && enteredJobLocation.contains("NS")) {
                // Attempt to get the access token
                getAccessToken(this, new AccessTokenListener() {
                    @Override
                    public void onAccessTokenReceived(String token) {
                        // When the token is received, send the notification
                        sendNotification(token);
                    }

                    @Override
                    public void onAccessTokenError(Exception exception) {
                        // Handle the error appropriately
                        Log.e("ERROR", exception.toString());
//                        Toast.makeText(PostFormActivity.this, "Error getting access token: " + exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }
            move2EmployerDashboard(job);

        } catch (IOException e) {
            errorMessage = getResources().getString(R.string.INVALID_LOCATION);
            result.setText(errorMessage);
            showToast(errorMessage);
            e.printStackTrace();
        }
    }

    /**
     * Gets current user id.
     *
     * @return the current user id
     */
    public String getCurrentUserID() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            return "TEST_UID";
        }
        return user.getUid();
    }

    /**
     * Move 2 employer dashboard.
     *
     * @param job the job
     */
    protected void move2EmployerDashboard(Job job) {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(this, EmployerListingsActivity.class);
            intent.putExtra("userID", job.getUserID());
            startActivity(intent);
            finish();
        }, 1000);
    }

    private void getAccessToken(Context context, AccessTokenListener listener) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                InputStream serviceAccountStream = context.getAssets().open(CREDENTIALS_FILE_PATH);
                GoogleCredentials googleCredentials = GoogleCredentials
                        .fromStream(serviceAccountStream)
                        .createScoped(Collections.singletonList("https://www.googleapis.com/auth/firebase.messaging"));

                googleCredentials.refreshIfExpired(); // This will refresh the token if it's expired
                String token = googleCredentials.getAccessToken().getTokenValue();
                listener.onAccessTokenReceived(token);
                Log.d("token","token"+token);
            } catch (IOException e) {
                listener.onAccessTokenError(e);
            }
        });
        executorService.shutdown();
    }

    private void sendNotification(String authToken) {
        try {
            // Build the notification payload
            JSONObject notificationJSONBody = new JSONObject();
            notificationJSONBody.put("title", "New Job Created");
            notificationJSONBody.put("body", "A new job is created in your city.");

            JSONObject dataJSONBody = new JSONObject();
            dataJSONBody.put("jobLocation", "Halifax, NS");
            dataJSONBody.put("job_id", jobId);

            JSONObject messageJSONBody = new JSONObject();
            messageJSONBody.put("topic", "nearbyJobs");
            messageJSONBody.put("notification", notificationJSONBody);
            messageJSONBody.put("data", dataJSONBody);

            JSONObject pushNotificationJSONBody = new JSONObject();
            pushNotificationJSONBody.put("message", messageJSONBody);

            // Log the complete JSON payload for debugging
            Log.d("NotificationBody", "JSON Body: " + pushNotificationJSONBody.toString());

            // Create the request
            JsonObjectRequest request = new JsonObjectRequest(
                    Request.Method.POST,
                    PUSH_NOTIFICATION_ENDPOINT,
                    pushNotificationJSONBody,
                    response -> {
                        Log.d("NotificationResponse", "Response: " + response.toString());
                        Toast.makeText(this, "Notification Sent Successfully", Toast.LENGTH_SHORT).show();
                    },
                    error -> {
                        Log.e("NotificationError", "Error Response: " + error.toString());
                        if (error.networkResponse != null) {
                            Log.e("NotificationError", "Status Code: " + error.networkResponse.statusCode);
                            Log.e("NotificationError", "Error Data: " + new String(error.networkResponse.data));
                        }
                        Toast.makeText(this, "Failed to Send Notification", Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=UTF-8");
                    headers.put("Authorization", "Bearer " + authToken);
                    Log.d("NotificationHeaders", "Headers: " + headers.toString());
                    return headers;
                }
            };

            // Add the request to the queue
            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("NotificationJSONException", "Error creating notification JSON: " + e.getMessage());
            Toast.makeText(this, "Error creating notification payload", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Add a setter for testing
    public void setJobsCRUD(Jobs jobsCRUD) {
        this.jobsCRUD = jobsCRUD;
    }
}