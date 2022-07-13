package com.example.celafoodapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.celafoodapp.databinding.ActivityCheckoutBinding;

public class CheckoutActivity extends AppCompatActivity {

    private static final String keyItem = "keyItem";
    private static final String keyPrice = "keyPrice";
    private ActivityCheckoutBinding binding;

    public static void starter(Context context, int totalItems, int totalPrice) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra(keyItem, totalItems);
        intent.putExtra(keyPrice, totalPrice);
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
            int totalItems = intent.getIntExtra(keyItem, 0);
            int totalPrice = intent.getIntExtra(keyPrice, 0);
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