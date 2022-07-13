package com.example.celafoodapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.celafoodapp.database.entity.Food;
import com.example.celafoodapp.databinding.FragmentHomeBinding;
import com.example.celafoodapp.ui.activity.DetailActivity;
import com.example.celafoodapp.ui.adapter.CategoryAdapter;
import com.example.celafoodapp.ui.adapter.FoodAdapter;
import com.example.celafoodapp.viewmodel.FoodViewModel;

public class HomeFragment extends Fragment {
    private final int SPAN_COUNT = 2;
    private final String CATEGORY_DEFAULT = "Cơm Eat Clean";

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
        observeRecyclerFood(CATEGORY_DEFAULT);

        displayRecyclerTitle();
        observeRecyclerTitle();
    }

    private void displayRecyclerFood() {
        binding.recyclerFood.setAdapter(foodAdapter);
        binding.recyclerFood.setLayoutManager(new GridLayoutManager(getContext(), SPAN_COUNT));
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