package com.example.celafoodapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.example.celafoodapp.databinding.ActivitySignInBinding;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.SetUpSharePreferences;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.SignInViewModel;

import java.util.Objects;

public class SignInActivity extends BaseActivity {
    private ActivitySignInBinding binding;
    private SignInViewModel signInViewModel;
    private SetUpSharePreferences setUpSharePreferences;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new ProgressDialog(this);
        dialog.setMessage("Signing...");

        SetUpSharePreferences.initSharePreferences(getApplicationContext());
        signInViewModel = new SignInViewModel(getApplicationContext());
        setUpSharePreferences = new SetUpSharePreferences();
        setUpSharePreferences.getData(SignInActivity.this);

        binding.signInButton.setOnClickListener(view -> {
            String email = binding.mailEdittext.getText().toString().trim();
            String password = binding.passwordEdittext.getText().toString().trim();
            boolean isEmailEmpty = TextUtils.isEmpty(Objects.requireNonNull(email));
            boolean isPasswordEmpty = TextUtils.isEmpty(Objects.requireNonNull(password));

            if (isEmailEmpty) {
                binding.mailEdittext.setError("Enter an E-Mail Address");
                binding.mailEdittext.requestFocus();
            } else if (isPasswordEmpty) {
                binding.passwordEdittext.setError("Enter a password");
                binding.passwordEdittext.requestFocus();
            }
            if (!isEmailEmpty && !isPasswordEmpty) {
                signInViewModel.getUser(email, password).observe(SignInActivity.this, user -> {
                    if (user != null) {
                        dialog.show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                setUpSharePreferences.setUpSharePreferences(true, user.getId());
                                startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                dialog.dismiss();
                                Utility.toast(getApplicationContext(), "Login successfully");
                                finish();
                            }
                        }, AppData.Config.DELAY_MILLIS);
                    } else {
                        Utility.toast(getApplicationContext(), "Email or Password is incorrect");
                    }
                });
            }
        });

        binding.signUpText.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
            finish();
        });
    }
}