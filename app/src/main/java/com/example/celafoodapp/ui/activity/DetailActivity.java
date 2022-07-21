package com.example.celafoodapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.R;
import com.example.celafoodapp.databinding.ActivityDetailBinding;
import com.example.celafoodapp.repository.local.entity.Cart;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.FoodViewModel;
import com.google.android.material.appbar.AppBarLayout;

import java.util.UUID;

public class DetailActivity extends BaseActivity {
    private ActivityDetailBinding binding;
    private FoodViewModel foodViewModel;

    public static void starter(Context context, Food food, String userId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(AppData.Key.food, food);
        intent.putExtra(AppData.Key.userId, userId);
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
            Food food = (Food) intent.getSerializableExtra(AppData.Key.food);
            if (food != null) {
                Glide.with(getApplicationContext())
                        .load(food.getImage())
                        .into(binding.imageDetail);
                binding.foodPrice.setText(food.getPrice());
                binding.collapsingToolbar.setTitle(food.getFoodName());

                if (food.getDescriptionEN() != null && food.getDescriptionVN() != null) {
                    String temp = food.getDescriptionVN().substring(12);
                    binding.thanhPhanContent.setText(temp);
                    binding.ingredientContent.setText(food.getDescriptionEN());
                } else {
                    binding.thanhPhanContent.setText("");
                    binding.ingredientContent.setText("");
                }
                binding.totalPrice.setText(food.getPrice());
                binding.countText.setText(String.valueOf(food.getAmount()));
                foodViewModel.setTotalItems(food.getAmount());

                foodViewModel.setFood(food);
            }
            foodViewModel.setUserId(intent.getStringExtra(AppData.Key.userId));
        }

        binding.minusText.setOnClickListener(view -> {
            foodViewModel.minusItems();
            binding.countText.setText(String.valueOf(foodViewModel.getTotalItems()));
            foodViewModel.setTotalPrice(pricing(foodViewModel.getTotalItems()));
            binding.totalPrice.setText(foodViewModel.getTotalPrice() + "đ");
        });

        binding.plusText.setOnClickListener(view -> {
            foodViewModel.plusItems();
            binding.countText.setText(String.valueOf(foodViewModel.getTotalItems()));
            foodViewModel.setTotalPrice(pricing(foodViewModel.getTotalItems()));
            binding.totalPrice.setText(foodViewModel.getTotalPrice() + "đ");
        });

        binding.addButton.setOnClickListener(view -> {
            String cartId = foodViewModel.getFood().getCartId();
            String userId = foodViewModel.getUserId();
            int foodId = foodViewModel.getFood().getId();
            int amount = Integer.parseInt(binding.countText.getText().toString());

            if (cartId == null) {
                cartId = UUID.randomUUID().toString();
                Cart cart = new Cart(cartId, userId, foodId, amount);
                executor.execute(() -> foodViewModel.insertCart(cart));
                Utility.toast(getApplicationContext(), "Add successfully");
            } else {
                AsyncTask.execute(() -> foodViewModel.updateCart(foodViewModel.getFood().getCartId(), amount, foodViewModel.getUserId()));
                finish();
                Utility.toast(getApplicationContext(), "Update successfully");
            }
        });

        binding.placeOrderButton.setOnClickListener(view -> {
            String userId = foodViewModel.getUserId();
            int foodId = foodViewModel.getFood().getId();
            int amount = Integer.parseInt(binding.countText.getText().toString());
            Order order = new Order(userId, foodId, amount);
            CheckoutActivity.starter(DetailActivity.this, foodViewModel.getTotalItems(), foodViewModel.getTotalPrice(), foodViewModel.getFood().getPrice(), order);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private int pricing(int numberOfItems) {
        String temp = ((foodViewModel.getFood().getPrice().substring(0, foodViewModel.getFood().getPrice().length() - 1)));
        String[] temp1 = temp.split(",");
        if (numberOfItems > 1) {
            return Integer.parseInt(temp1[0].concat(temp1[1])) * numberOfItems;
        } else {
            return Integer.parseInt(temp1[0].concat(temp1[1])) * 1;
        }
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
}