package com.example.celafoodapp.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;

import com.example.celafoodapp.databinding.ActivitySignUpBinding;
import com.example.celafoodapp.repository.local.entity.User;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.SetUpSharePreferences;
import com.example.celafoodapp.viewmodel.SignUpViewModel;

import java.util.Objects;
import java.util.UUID;

public class SignUpActivity extends BaseActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;
    private ProgressDialog dialog;
    private SetUpSharePreferences setUpSharePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signUpViewModel = new SignUpViewModel(getApplicationContext());
        setUpSharePreferences = new SetUpSharePreferences();
        SetUpSharePreferences.initSharePreferences(getApplicationContext());

        dialog = new ProgressDialog(this);
        dialog.setMessage("We're creating an new account...");

        binding.signUpButton.setOnClickListener(view -> {
            String userId = UUID.randomUUID().toString();
            String email = binding.mailEdittext.getText().toString().trim();
            String name = binding.nameEdittext.getText().toString().trim();
            String password = binding.passwordEdittext.getText().toString().trim();
            User user = new User(userId, email, name, password);

            if (TextUtils.isEmpty(Objects.requireNonNull(user).getEmail())) {
                binding.mailEdittext.setError("Enter an E-Mail Address");
                binding.mailEdittext.requestFocus();
            } else if (!user.isEmailValid()) {
                binding.mailEdittext.setError("Enter a valid email address");
                binding.mailEdittext.requestFocus();
            } else if (TextUtils.isEmpty(Objects.requireNonNull(user).getName())) {
                binding.nameEdittext.setError("Enter your name");
                binding.nameEdittext.requestFocus();
            } else if (!user.isPasswordLengthGreaterThan6()) {
                binding.passwordEdittext.setError("Enter at least 6 digit password");
                binding.passwordEdittext.requestFocus();
            } else {
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        executor.execute(new Runnable() {
                            @Override
                            public void run() {
                                setUpSharePreferences.setUpSharePreferences(true, userId);
                                signUpViewModel.insertUser(user);
                            }
                        });
                        dialog.dismiss();
                        startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                        finish();
                    }
                }, AppData.Config.DELAY_MILLIS);
            }
        });

        binding.signInText.setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
        });
    }
}