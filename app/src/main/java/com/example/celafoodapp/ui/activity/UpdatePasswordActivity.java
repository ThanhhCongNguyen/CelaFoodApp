package com.example.celafoodapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.example.celafoodapp.databinding.ActivityUpdatePasswordBinding;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.SetUpSharePreferences;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.UpdatePasswordViewModel;

import java.util.Objects;

public class UpdatePasswordActivity extends BaseActivity {
    private ActivityUpdatePasswordBinding binding;
    private UpdatePasswordViewModel updatePasswordViewModel;
    private SetUpSharePreferences setUpSharePreferences;
    private ProgressDialog dialog;

    public static void starter(Context context, String userId) {
        Intent intent = new Intent(context, UpdatePasswordActivity.class);
        intent.putExtra(AppData.Key.userId, userId);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdatePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        updatePasswordViewModel = new UpdatePasswordViewModel(this);
        setUpSharePreferences = new SetUpSharePreferences();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Updating...");

        Intent intent = getIntent();
        if (intent != null) {
            String userId = intent.getStringExtra(AppData.Key.userId);
            updatePasswordViewModel.setUserId(userId);
        }

        binding.backIcon.setOnClickListener(view -> {
            finish();
        });

        binding.update.setOnClickListener(view -> {
            String newPassword = binding.newPasswordEdittext.getText().toString().trim();
            boolean isNewPassEmpty = TextUtils.isEmpty(Objects.requireNonNull(newPassword));

            if (isNewPassEmpty) {
                binding.newPasswordEdittext.setError("Enter a new password");
                binding.newPasswordEdittext.requestFocus();
            }
            if (!isNewPassEmpty) {
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        executor.execute(() -> updatePasswordViewModel.updatePassword(updatePasswordViewModel.getUserId(), newPassword));
                        startActivity(new Intent(UpdatePasswordActivity.this, SignInActivity.class));
                        Utility.toast(UpdatePasswordActivity.this, "Update successfully");
                        setUpSharePreferences.setUpLogout(false);
                        dialog.dismiss();
                        finish();
                    }
                }, AppData.Config.DELAY_MILLIS);
            }
        });
    }
}