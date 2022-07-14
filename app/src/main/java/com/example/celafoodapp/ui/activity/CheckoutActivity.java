package com.example.celafoodapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.celafoodapp.databinding.ActivityCheckoutBinding;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.AppData;

public class CheckoutActivity extends BaseActivity {
    private ActivityCheckoutBinding binding;

    public static void starter(Context context, int totalItems, int totalPrice) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra(AppData.Key.keyItem, totalItems);
        intent.putExtra(AppData.Key.keyPrice, totalPrice);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            int totalItems = intent.getIntExtra(AppData.Key.keyItem, 0);
            int totalPrice = intent.getIntExtra(AppData.Key.keyPrice, 0);
            binding.price.setText(String.valueOf(totalItems));
            binding.totalPrice.setText(totalPrice + "đ");
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}