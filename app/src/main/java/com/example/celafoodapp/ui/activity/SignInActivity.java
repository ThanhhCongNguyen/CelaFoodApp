package com.example.celafoodapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.celafoodapp.databinding.ActivitySignInBinding;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.SignInViewModel;

import java.util.Objects;

public class SignInActivity extends BaseActivity {
    private ActivitySignInBinding binding;
    private SignInViewModel signInViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SignInViewModel.initSharePreferences(getApplicationContext());
        signInViewModel = new SignInViewModel(getApplicationContext());
        signInViewModel.getData(SignInActivity.this);

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
                        startActivity(new Intent(this, MainActivity.class));
                        signInViewModel.setUpSharePreferences(true);
                        Utility.toast(getApplicationContext(), "Login successfully");
                    } else {
                        Utility.toast(getApplicationContext(), "Email or Password is incorrect");
                    }
                });
            }
        });

        binding.signUpText.setOnClickListener(view -> {
            startActivity(new Intent(this, SignUpActivity.class));
        });
    }
}