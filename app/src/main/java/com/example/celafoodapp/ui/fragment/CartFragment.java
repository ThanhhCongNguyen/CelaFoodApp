package com.example.celafoodapp.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.celafoodapp.R;
import com.example.celafoodapp.databinding.FragmentCartBinding;
import com.example.celafoodapp.repository.local.entity.Cart;
import com.example.celafoodapp.repository.local.entity.CartContent;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.ui.activity.CheckoutActivity;
import com.example.celafoodapp.ui.activity.DetailActivity;
import com.example.celafoodapp.ui.activity.MainActivity;
import com.example.celafoodapp.ui.adapter.CartAdapter;
import com.example.celafoodapp.ui.base.BaseFragment;
import com.example.celafoodapp.util.AppData;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.FoodViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class CartFragment extends BaseFragment {
    private FragmentCartBinding binding;
    private FoodViewModel foodViewModel;
    private CartAdapter cartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new FoodViewModel(getContext());

        Bundle bundle = getArguments();
        if (bundle != null) {
            String userId = bundle.getString(AppData.Key.userId);
            foodViewModel.setUserId(userId);
        }

        cartAdapter = new CartAdapter(new CartAdapter.Callback() {
            @Override
            public void minus(String id, int amount) {
                minusAction(id, amount);
            }

            @Override
            public void plus(String id, int amount) {
                plusAction(id, amount);
            }

            @Override
            public void checkout(int amount, int totalPrice, String price, Order order) {
                CheckoutActivity.starter(getContext(), amount, totalPrice, price, order);
            }

            @Override
            public void itemOnClick(Food food) {
                DetailActivity.starter(getContext(), food, foodViewModel.getUserId());
            }

            @Override
            public void deleteItem(Cart cart, int position) {
                executor.execute(() -> foodViewModel.deleteCart(cart));
                Snackbar snackbar = Snackbar.make(binding.recyclerCart, "Deleted", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        executor.execute(() -> foodViewModel.insertCart(cart));
                        cartAdapter.notifyItemInserted(position);
                    }
                });
                snackbar.getView().setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.green));
                snackbar.show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        displayCartRecyclerView();
        observeCartRecyclerView();
        binding.layoutEmpty.shopButton.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(), MainActivity.class));
        });

    }

    private void displayCartRecyclerView() {
        binding.recyclerCart.setAdapter(cartAdapter);
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CartAdapter.SwipeToDeleteCallback(cartAdapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerCart);
    }

    private void observeCartRecyclerView() {
        foodViewModel.getAllCart(foodViewModel.getUserId()).observe(getViewLifecycleOwner(), cartContents -> {
            if (cartContents != null) {
                if (cartContents.size() > 0) {
                    binding.recyclerCart.setVisibility(View.VISIBLE);
                    binding.myCartTextView.setVisibility(View.VISIBLE);
                    binding.layoutEmpty.parent.setVisibility(View.GONE);
                } else {
                    binding.recyclerCart.setVisibility(View.GONE);
                    binding.myCartTextView.setVisibility(View.GONE);
                    binding.layoutEmpty.parent.setVisibility(View.VISIBLE);
                }
                cartAdapter.setCarts(cartContents);
                setTotal(cartContents);
                foodViewModel.setCartContents(cartContents);
            }
        });
    }

    private void setTotal(List<CartContent> cartContents) {
        int priceTemp = 0;
        foodViewModel.setTotalItems(cartContents.size());
        foodViewModel.setTotalPrice(0);

        for (CartContent cartContent : cartContents) {
            priceTemp += Utility.pricing(cartContent);
        }
        foodViewModel.setTotalPrice(priceTemp);
    }

    private void plusAction(String id, int amount) {
        amount++;
        int finalAmount = amount;
        executor.execute(() -> foodViewModel.updateCart(id, finalAmount, foodViewModel.getUserId()));
    }

    private void minusAction(String id, int amount) {
        if (amount > 1) {
            amount--;
            int finalAmount = amount;
            executor.execute(() -> foodViewModel.updateCart(id, finalAmount, foodViewModel.getUserId()));
        }
    }
}