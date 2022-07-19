package com.example.celafoodapp.ui.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;

import com.example.celafoodapp.databinding.ActivitySignUpBinding;
import com.example.celafoodapp.repository.local.entity.User;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.SignUpViewModel;

import java.util.Objects;

public class SignUpActivity extends BaseActivity {
    private ActivitySignUpBinding binding;
    private SignUpViewModel signUpViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        signUpViewModel = new SignUpViewModel(getApplicationContext());

        binding.signUpButton.setOnClickListener(view -> {
            String email = binding.mailEdittext.getText().toString().trim();
            String name = binding.nameEdittext.getText().toString().trim();
            String password = binding.passwordEdittext.getText().toString().trim();
            User user = new User(email, name, password);

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
                AsyncTask.execute(() -> signUpViewModel.insertUser(user));
                Utility.toast(getApplicationContext(), "Sign Up  successfully");
            }
        });

        binding.signInText.setOnClickListener(view -> {
            startActivity(new Intent(this, SignInActivity.class));
        });
    }
}