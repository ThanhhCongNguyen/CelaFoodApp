package com.example.celafoodapp.ui.activity;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.celafoodapp.R;
import com.example.celafoodapp.local.entity.User;
import com.example.celafoodapp.databinding.ActivitySignInBinding;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.LoginViewModel;

import java.util.Objects;

public class SignInActivity extends BaseActivity {
    private ActivitySignInBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        binding = DataBindingUtil.setContentView(SignInActivity.this, R.layout.activity_sign_in);
        binding.setLifecycleOwner(this);
        binding.setLoginViewModel(loginViewModel);

        binding.signInButton.setOnClickListener(view -> {
            String email = binding.mailEdittext.getText().toString().trim();
            String password = binding.passwordEdittext.getText().toString().trim();
            User user = new User(email, password);
            loginViewModel.onClick(view, user);
        });

        loginViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (TextUtils.isEmpty(Objects.requireNonNull(user).getEmail())) {
                    binding.mailEdittext.setError("Enter an E-Mail Address");
                    binding.mailEdittext.requestFocus();
                } else if (!user.isEmailValid()) {
                    binding.mailEdittext.setError("Enter a valid email address");
                    binding.mailEdittext.requestFocus();
                } else if (!user.isPasswordLengthGreaterThan6()) {
                    binding.passwordEdittext.setError("Enter at least 6 digit password");
                    binding.passwordEdittext.requestFocus();
                } else {
                    Utility.toast(getApplicationContext(), "Login successfully");
                }
            }
        });

    }
}