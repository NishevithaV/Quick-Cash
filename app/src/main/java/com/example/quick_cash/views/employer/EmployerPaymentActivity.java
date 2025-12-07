package com.example.quick_cash.views.employer;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quick_cash.controllers.PaymentValidator;

import com.example.quick_cash.R;
import com.example.quick_cash.utils.FirebaseCRUD.Applications;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import java.math.BigDecimal;

/**
 * The type Employer payment activity.
 */
public class EmployerPaymentActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 999;

    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AcER7b7g1vj0zzcMHlTH2b7dBnjL8NkMB2sJ13Ct1ChNStlAzKoW8rud9XbOVv5Ldx8GvQlgXjydoXEt");

    private EditText amountEditText;
    private Button payButton;

    private String jobApplicationId;

    private Applications appsCRUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        appsCRUD = new Applications(FirebaseDatabase.getInstance());

// Receive application ID
        jobApplicationId = getIntent().getStringExtra("jobApplicationId");
        if (jobApplicationId == null) {
            Toast.makeText(this, "Missing application ID", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

// Start PayPal service
        Intent serviceIntent = new Intent(this, PayPalService.class);
        serviceIntent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(serviceIntent);

        amountEditText = findViewById(R.id.amountEditText);
        payButton = findViewById(R.id.payButton);

// Inside payButton click listener
        payButton.setOnClickListener(v -> {
            String amountStr = amountEditText.getText().toString();
            if (!PaymentValidator.isAmountValid(amountStr)) {
                Toast.makeText(this, "Enter a valid amount", Toast.LENGTH_SHORT).show();
                return;
            }

            BigDecimal amount = PaymentValidator.parseAmount(amountStr);
            startPayPalPayment(amount);
        });


    }

    private void startPayPalPayment(BigDecimal amount) {

        PayPalPayment payment = new PayPalPayment(
                amount,
                "CAD",
                "QuickCash Job Payment",
                PayPalPayment.PAYMENT_INTENT_SALE
        );

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {

                        // Update Firebase: status = approved
                        appsCRUD.updateStatus(jobApplicationId, "paid");

                        // Send result back to ApplicationReviewActivity
                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("paymentApproved", true);
                        setResult(RESULT_OK, resultIntent);

                        finish();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Payment canceled", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
