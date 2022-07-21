package com.example.celafoodapp.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.example.celafoodapp.R;
import com.example.celafoodapp.databinding.ActivityCheckoutBinding;
import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.viewmodel.CheckoutViewModel;

public class CheckoutActivity extends BaseActivity {
    private ActivityCheckoutBinding binding;
    private CheckoutViewModel checkoutViewModel;
    private ProgressDialog dialog;

    public static void starter(Context context, int totalItems, int totalPrice, String price, Order orderObject) {
        Intent intent = new Intent(context, CheckoutActivity.class);
        intent.putExtra(AppData.Key.item, totalItems);
        intent.putExtra(AppData.Key.totalPrice, totalPrice);
        intent.putExtra(AppData.Key.price, price);
        intent.putExtra(AppData.Key.orderObject, orderObject);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        checkoutViewModel = new CheckoutViewModel(getApplicationContext());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Waiting...");

        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            int totalItems = intent.getIntExtra(AppData.Key.item, 0);
            int totalPrice = intent.getIntExtra(AppData.Key.totalPrice, 0);
            String price = intent.getStringExtra(AppData.Key.price);
            Order order = (Order) intent.getSerializableExtra(AppData.Key.orderObject);
            checkoutViewModel.setOrder(order);
            binding.price.setText(price);
            binding.amount.setText(String.valueOf(totalItems));
            binding.totalPrice.setText(totalPrice + "đ");
        }

        binding.cancelButton.setOnClickListener(view -> {
            finish();
        });

        binding.payButton.setOnClickListener(view -> {
            dialog.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    executor.execute(() -> checkoutViewModel.insertOrder(checkoutViewModel.getOrder()));
                    showAlertDialog(R.layout.dialog_success);
                    dialog.dismiss();
                }
            }, AppData.Config.DELAY_MILLIS);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private void showAlertDialog(int view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        View layoutView = getLayoutInflater().inflate(view, null);

        Button backButton = layoutView.findViewById(R.id.backButton);
        alertDialogBuilder.setView(layoutView);
        AlertDialog dialog = alertDialogBuilder.create();
        dialog.getWindow().setBackgroundDrawableResource(cn.pedant.SweetAlert.R.color.float_transparent);
        dialog.show();

        backButton.setOnClickListener(view1 -> {
            startActivity(new Intent(CheckoutActivity.this, MainActivity.class));
        });
    }
}