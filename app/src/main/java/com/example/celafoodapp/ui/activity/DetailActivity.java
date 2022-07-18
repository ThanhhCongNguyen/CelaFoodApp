package com.example.celafoodapp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.example.celafoodapp.R;
import com.example.celafoodapp.local.entity.Cart;
import com.example.celafoodapp.local.entity.Food;
import com.example.celafoodapp.local.entity.Order;
import com.example.celafoodapp.databinding.ActivityDetailBinding;
import com.example.celafoodapp.ui.base.BaseActivity;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.FoodViewModel;
import com.google.android.material.appbar.AppBarLayout;

public class DetailActivity extends BaseActivity {

    private ActivityDetailBinding binding;
    private FoodViewModel foodViewModel;
    //    private int numberOfItems = 1;
    private Food food;

    public static void starter(Context context, Food food) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(AppData.Key.food, food);
        context.startActivity(intent);
    }

    public static void idStarter(Context context, int foodId) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(AppData.Key.foodId, foodId);
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
            food = (Food) intent.getSerializableExtra(AppData.Key.food);
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
            }

            int foodId = intent.getIntExtra(AppData.Key.foodId, 0);
        }

        int cartId = food.getCartId();
        Log.d("tag", cartId + "");

        binding.minusText.setOnClickListener(view -> {
//            if (foodViewModel.getTotalItems() > 1) {
//                foodViewModel.setTotalItems();
//                binding.countText.setText(String.valueOf(numberOfItems));
//                binding.totalPrice.setText(pricing(numberOfItems) + "đ");
//            } else {
//                return;
//            }
            foodViewModel.minusItems();
            binding.countText.setText(String.valueOf(foodViewModel.getTotalItems()));
            foodViewModel.setTotalPrice(pricing(foodViewModel.getTotalItems()));
            // binding.totalPrice.setText(pricing(foodViewModel.getTotalPrice()) + "đ");
            binding.totalPrice.setText(foodViewModel.getTotalPrice() + "đ");
        });

        binding.plusText.setOnClickListener(view -> {
//            numberOfItems++;
//            binding.countText.setText(String.valueOf(numberOfItems));
//            binding.totalPrice.setText(pricing(numberOfItems) + "đ");
            foodViewModel.plusItems();
            binding.countText.setText(String.valueOf(foodViewModel.getTotalItems()));
            foodViewModel.setTotalPrice(pricing(foodViewModel.getTotalItems()));
            // binding.totalPrice.setText(pricing(foodViewModel.getTotalItems()) + "đ");
            binding.totalPrice.setText(foodViewModel.getTotalPrice() + "đ");
        });

        binding.addButton.setOnClickListener(view -> {
            int userId = 100;
            int foodId = food.getId();
            int amount = Integer.parseInt(binding.countText.getText().toString());
            Cart cart = new Cart(userId, foodId, amount);
            AsyncTask.execute(() -> foodViewModel.insertCart(cart));
//            foodViewModel.insertCart(cart);
            Utility.toast(getApplicationContext(), "Add successfully");
        });

        binding.placeOrderButton.setOnClickListener(view -> {
            int userId = 100;
            int foodId = food.getId();
            int amount = Integer.parseInt(binding.countText.getText().toString());
            Order order = new Order(userId, foodId, amount);
            AsyncTask.execute(() -> foodViewModel.insertOrder(order));
//            foodViewModel.insertCart(cart);
            //Utility.toast(getApplicationContext(), "Add successfully");
            CheckoutActivity.starter(DetailActivity.this, foodViewModel.getTotalItems(), foodViewModel.getTotalPrice());
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }

    private int pricing(int numberOfItems) {
        String temp = ((food.getPrice().substring(0, food.getPrice().length() - 1)));
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