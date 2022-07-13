package com.example.celafoodapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.R;
import com.example.celafoodapp.database.entity.Cart;
import com.example.celafoodapp.database.entity.Food;
import com.example.celafoodapp.databinding.ActivityDetailBinding;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.FoodViewModel;
import com.google.android.material.appbar.AppBarLayout;

public class DetailActivity extends AppCompatActivity {

    private static final String keyFood = "key";

    private ActivityDetailBinding binding;
    private FoodViewModel foodViewModel;
    private int numberOfItems = 1;
    private Food food;

    public static void starter(Context context, Food food) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(keyFood, food);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        foodViewModel = new FoodViewModel(getApplicationContext());
        initToolbar();

        Intent intent = getIntent();
        if (intent != null) {
            food = (Food) intent.getSerializableExtra(keyFood);
            if (food != null) {
                Glide.with(getApplicationContext())
                        .load(food.getImage())
                        .placeholder(R.drawable.loading)
                        .into(binding.imageDetail);
                binding.foodPrice.setText(food.getPrice());
                binding.collapsingToolbar.setTitle(food.getFoodName());

                String temp = food.getDescriptionVN().substring(12);
                binding.thanhPhanContent.setText(temp);
                binding.ingredientContent.setText(food.getDescriptionEN());
                binding.totalPrice.setText(food.getPrice());
            }
        }

        binding.minusText.setOnClickListener(view -> {
            if (numberOfItems > 1) {
                numberOfItems--;
                binding.countText.setText(String.valueOf(numberOfItems));
                binding.totalPrice.setText(pricing(numberOfItems) + "đ");
            } else {
                return;
            }
        });

        binding.plusText.setOnClickListener(view -> {
            numberOfItems++;
            binding.countText.setText(String.valueOf(numberOfItems));
            binding.totalPrice.setText(pricing(numberOfItems) + "đ");
        });

        binding.addButton.setOnClickListener(view -> {
            int userId = 100;
            int foodId = food.getId();
            int amount = Integer.parseInt(binding.countText.getText().toString());
            Cart cart = new Cart(userId, foodId, amount);
            foodViewModel.insertCart(cart);
            Utility.toast(getApplicationContext(), "Add successfully");
        });
    }

    private int pricing(int numberOfItems) {
        String temp = ((food.getPrice().substring(0, food.getPrice().length() - 1)));
        String[] temp1 = temp.split(",");
        return Integer.parseInt(temp1[0].concat(temp1[1])) * numberOfItems;
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);


        binding.collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        binding.collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if ((binding.collapsingToolbar.getHeight() + verticalOffset) < (2 * ViewCompat.getMinimumHeight(binding.collapsingToolbar))) {
                    binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                } else {
                    binding.toolbar.getNavigationIcon().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                }
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}