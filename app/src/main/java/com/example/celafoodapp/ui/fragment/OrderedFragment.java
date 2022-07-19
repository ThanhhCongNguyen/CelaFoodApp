package com.example.celafoodapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.databinding.FragmentOrderedBinding;
import com.example.celafoodapp.ui.activity.DetailActivity;
import com.example.celafoodapp.ui.adapter.OrderAdapter;
import com.example.celafoodapp.ui.base.BaseFragment;
import com.example.celafoodapp.viewmodel.FoodViewModel;

public class OrderedFragment extends BaseFragment {
    private FragmentOrderedBinding binding;
    private FoodViewModel foodViewModel;
    private OrderAdapter orderAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new FoodViewModel(getContext());
        orderAdapter = new OrderAdapter(new OrderAdapter.Callback() {
            @Override
            public void onItemClick(Food food) {
                DetailActivity.starter(getContext(), food);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderedBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayOrder();
        observeOrder();


    }

    private void displayOrder() {
        binding.recyclerview.setAdapter(orderAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observeOrder() {
        foodViewModel.getAllOrder().observe(getViewLifecycleOwner(), orderContents -> {
            if (orderContents != null) {
                orderAdapter.setOrderContents(orderContents);
            }
        });
    }


}