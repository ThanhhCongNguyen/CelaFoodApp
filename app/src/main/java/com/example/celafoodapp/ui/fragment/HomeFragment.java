package com.example.celafoodapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.celafoodapp.local.entity.Food;
import com.example.celafoodapp.databinding.FragmentHomeBinding;
import com.example.celafoodapp.ui.activity.DetailActivity;
import com.example.celafoodapp.ui.adapter.CategoryAdapter;
import com.example.celafoodapp.ui.adapter.FoodAdapter;
import com.example.celafoodapp.ui.base.BaseFragment;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.viewmodel.FoodViewModel;

public class HomeFragment extends BaseFragment {
    private FragmentHomeBinding binding;
    private FoodViewModel foodViewModel;
    private FoodAdapter foodAdapter;
    private CategoryAdapter categoryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new FoodViewModel(getContext());
        foodAdapter = new FoodAdapter(new FoodAdapter.CallBack() {
            @Override
            public void onItemClick(Food food) {
                DetailActivity.starter(getContext(), food);
            }
        });
        categoryAdapter = new CategoryAdapter(new CategoryAdapter.CallBack() {
            @Override
            public void onItemClick(String category) {
                observeRecyclerFood(category);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayRecyclerFood();
        observeRecyclerFood(AppData.Config.CATEGORY_DEFAULT);

        displayRecyclerTitle();
        observeRecyclerTitle();
    }

    private void displayRecyclerFood() {
        binding.recyclerFood.setAdapter(foodAdapter);
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(getContext(), AppData.Config.SPAN_COUNT));
    }

    private void observeRecyclerFood(String category) {
        foodViewModel.getFood(category).observe(getViewLifecycleOwner(), foods -> {
            foodAdapter.setFoods(foods);
        });
    }

    private void displayRecyclerTitle() {
        binding.recyclerCategory.setAdapter(categoryAdapter);
        binding.recyclerCategory.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void observeRecyclerTitle() {
        foodViewModel.getCategoryTitle().observe(getViewLifecycleOwner(), titles -> {
            categoryAdapter.setTitles(titles);
        });
    }
}