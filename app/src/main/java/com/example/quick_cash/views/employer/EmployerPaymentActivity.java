package com.example.quick_cash.views.employer;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.quick_cash.controllers.PaymentValidator;

import com.example.quick_cash.R;
import com.google.firebase.database.FirebaseDatabase;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.PaymentConfirmation;


import java.math.BigDecimal;

public class EmployerPaymentActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 999;

    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AcER7b7g1vj0zzcMHlTH2b7dBnjL8NkMB2sJ13Ct1ChNStlAzKoW8rud9XbOVv5Ldx8GvQlgXjydoXEt");

    private EditText amountEditText;
    private Button payButton;

    private String jobApplicationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

    }

    private void startPayPalPayment(BigDecimal amount) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    @Override
    protected void onDestroy() {

    }
            }
