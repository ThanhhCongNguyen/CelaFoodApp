package com.example.celafoodapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.celafoodapp.databinding.FragmentProfileBinding;
import com.example.celafoodapp.ui.activity.SignInActivity;
import com.example.celafoodapp.ui.activity.UpdatePasswordActivity;
import com.example.celafoodapp.ui.base.BaseFragment;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.SetUpSharePreferences;
import com.example.celafoodapp.viewmodel.FoodViewModel;

public class ProfileFragment extends BaseFragment {
    private FragmentProfileBinding binding;
    private SetUpSharePreferences sharedPreferences;
    private FoodViewModel foodViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = new SetUpSharePreferences();
        foodViewModel = new FoodViewModel(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            String userId = bundle.getString(AppData.Key.userId);
            foodViewModel.setUserId(userId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        foodViewModel.getUserById(foodViewModel.getUserId()).observe(getViewLifecycleOwner(), user -> {
            binding.name.setText(user.getName());
            binding.gmail.setText(user.getEmail());
        });

        binding.changePassword.setOnClickListener(view1 -> {
            UpdatePasswordActivity.starter(getContext(), foodViewModel.getUserId());
        });

        binding.logout.setOnClickListener(view1 -> {
            sharedPreferences.setUpLogout(false);
            startActivity(new Intent(getActivity(), SignInActivity.class));
            getActivity().finish();
        });
    }
}