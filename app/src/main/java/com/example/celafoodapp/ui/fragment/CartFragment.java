package com.example.celafoodapp.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.celafoodapp.databinding.FragmentCartBinding;
import com.example.celafoodapp.repository.local.entity.CartContent;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.ui.activity.CheckoutActivity;
import com.example.celafoodapp.ui.activity.DetailActivity;
import com.example.celafoodapp.ui.adapter.CartAdapter;
import com.example.celafoodapp.ui.base.BaseFragment;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.FoodViewModel;

import java.util.List;

public class CartFragment extends BaseFragment {
    private FragmentCartBinding binding;
    private FoodViewModel foodViewModel;
    private CartAdapter cartAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        foodViewModel = new FoodViewModel(getContext());
        cartAdapter = new CartAdapter(new CartAdapter.Callback() {
            @Override
            public void minus(int id, int amount) {
                minusAction(id, amount);
            }

            @Override
            public void plus(int id, int amount) {
                plusAction(id, amount);
            }

            @Override
            public void checkout(int amount, int price) {
                CheckoutActivity.starter(getContext(), amount, price);
            }

            @Override
            public void itemOnClick(Food food) {
                DetailActivity.starter(getContext(), food);
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

        binding.placeOrderedButton.setOnClickListener(view1 -> {
            for (CartContent cartContent : foodViewModel.getCartContents()) {
                int userId = cartContent.getUserId();
                int foodId = cartContent.getFoodId();
                int amount = cartContent.getAmount();
                Order order = new Order(userId, foodId, amount);
                foodViewModel.addOrder(order);
            }
            AsyncTask.execute(() -> foodViewModel.insertMultipleRows(foodViewModel.getOrderContents()));
            // CheckoutActivity.starter(getContext(), foodViewModel.getTotalItems(), foodViewModel.getTotalPrice());
        });
    }

    private void displayCartRecyclerView() {
        binding.recyclerCart.setAdapter(cartAdapter);
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new CartAdapter.SwipeToDeleteCallback(cartAdapter));
        itemTouchHelper.attachToRecyclerView(binding.recyclerCart);
    }

    private void observeCartRecyclerView() {
        foodViewModel.getAllCart().observe(getViewLifecycleOwner(), cartContents -> {
            if (cartContents != null) {
                cartAdapter.setCarts(cartContents);
                setTotal(cartContents);
                Log.d("tag", "update");
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
        binding.subTotalTextView.setText("Total (" + foodViewModel.getTotalItems() + " items): ");
        binding.totalPrice.setText(foodViewModel.getTotalPrice() + "đ");
    }

    private void plusAction(int id, int amount) {
        int amount1 = amount++;
//       // int finalAmount = amount;
        int finalAmount = amount1;
        AsyncTask.execute(() -> foodViewModel.updateCart(id, finalAmount));
        Log.d("tag", "+");
    }

    private void minusAction(int id, int amount) {
//        if (amount > 1) {
//            amount--;
//            //int finalAmount = amount;
//            AsyncTask.execute(() -> foodViewModel.updateCart(id, finalAmount));
//        }
    }
}