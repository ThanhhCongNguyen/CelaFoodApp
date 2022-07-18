package com.example.celafoodapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.celafoodapp.databinding.ActivityPaymentBinding;
import com.example.celafoodapp.ui.base.BaseActivity;

public class PaymentActivity extends BaseActivity {
    private ActivityPaymentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backButton.setOnClickListener(view -> {
            startActivity(new Intent(PaymentActivity.this, MainActivity.class));
        });
    }
}