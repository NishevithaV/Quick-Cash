package com.example.quick_cash.views.switch_role;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quick_cash.utils.FirebaseCRUD.Users;
import com.example.quick_cash.R;
import com.example.quick_cash.views.employee.EmployeeDashboardActivity;
import com.example.quick_cash.views.employer.EmployerDashboardActivity;
import com.google.firebase.database.FirebaseDatabase;

public class ConfirmActivity extends AppCompatActivity {

    private EditText emailInput;
    private Button confirmButton;
    private Button cancelButton;
    private TextView roleSwitchText;
    private String currentUserEmail;
    private String userId;
    private String newRole;
    private Users usersDb;

    /**
     * Gets users db.
     *
     * @return the users db
     */
// Allow dependency injection for testing
    protected Users getUsersDb() {
        if (usersDb == null) {
            usersDb = new Users(FirebaseDatabase.getInstance());
        }
        return usersDb;
    }

    /**
     * Sets users db.
     *
     * @param usersDb the users db
     */
// For testing purposes
    public void setUsersDb(Users usersDb) {
        this.usersDb = usersDb;
    }

    /**
     * Emails match boolean.
     *
     * @param email1 the email 1
     * @param email2 the email 2
     * @return the boolean
     */
    public static boolean emailsMatch(String email1, String email2){
        return email1.equals(email2);
    }

    /**
     * Overriden onCreate function to start activity, initialize UI, properties, and set listeners
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_confirm);

        // Initialize Firebase CRUD
        usersDb = getUsersDb();

        // Get data from intent
        String currentRole = getIntent().getStringExtra("current_role");
        newRole = getIntent().getStringExtra("new_role");
        currentUserEmail = getIntent().getStringExtra("current_email");
        userId = getIntent().getStringExtra("user_id");

        initUI();


        String message = "You are now switching from " + currentRole + " to " + newRole + ". Input your email to proceed.";
        roleSwitchText.setText(message);

        initListeners();


    }

    private void initListeners() {
        confirmButton.setOnClickListener(v -> {
            String enteredEmail = emailInput.getText().toString().trim();
            if (enteredEmail.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            if (emailsMatch(currentUserEmail, enteredEmail)) {
                usersDb.updateUserRole(userId, newRole, new Users.RoleUpdateCallback() {
                    @Override
                    public void onSuccess() {
                        // Navigate directly to the correct dashboard
                        Intent intent;
                        if (newRole.equalsIgnoreCase("employee")) {
                            intent = new Intent(ConfirmActivity.this, EmployeeDashboardActivity.class);
                        } else {
                            intent = new Intent(ConfirmActivity.this, EmployerDashboardActivity.class);
                        }
                        intent.putExtra("show_success_message", true);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(ConfirmActivity.this, "Failed to switch role: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Toast.makeText(this, "Email does not match your current logged-in account",
                        Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> finish());
    }

    private void initUI() {
        roleSwitchText = findViewById(R.id.roleSwitchText);
        emailInput = findViewById(R.id.roleInputEmail);
        confirmButton = findViewById(R.id.roleConfirmButton);
        cancelButton = findViewById(R.id.roleCancelButton);roleSwitchText = findViewById(R.id.roleSwitchText);
        emailInput = findViewById(R.id.roleInputEmail);
        confirmButton = findViewById(R.id.roleConfirmButton);
        cancelButton = findViewById(R.id.roleCancelButton);
    }
}