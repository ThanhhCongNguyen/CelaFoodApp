package com.example.celafoodapp.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.celafoodapp.database.entity.CartContent;
import com.example.celafoodapp.databinding.FragmentCartBinding;
import com.example.celafoodapp.ui.activity.CheckoutActivity;
import com.example.celafoodapp.ui.adapter.CartAdapter;
import com.example.celafoodapp.ui.base.BaseFragment;
import com.example.celafoodapp.util.Utility;
import com.example.celafoodapp.viewmodel.FoodViewModel;

import java.util.List;

public class CartFragment extends BaseFragment {
    private FragmentCartBinding binding;
    private FoodViewModel foodViewModel;
    private CartAdapter cartAdapter;
    private int totalItems = 0;
    private int totalPrice = 0;

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
            CheckoutActivity.starter(getContext(), totalItems, totalPrice);
        });
    }

    private void displayCartRecyclerView() {
        binding.recyclerCart.setAdapter(cartAdapter);
        binding.recyclerCart.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void observeCartRecyclerView() {
        foodViewModel.getAllCart().observe(getViewLifecycleOwner(), cartContents -> {
            cartAdapter.setCarts(cartContents);
            setTotal(cartContents);
        });
    }

    private void setTotal(List<CartContent> cartContents) {
        totalItems = cartContents.size();
        totalPrice = 0;

        for (CartContent cartContent : cartContents) {
            totalPrice += Utility.pricing(cartContent);
        }

        binding.subTotalTextView.setText("Total (" + totalItems + " items): ");
        binding.totalPrice.setText(totalPrice + "đ");
    }

    private void plusAction(int id, int amount) {
        amount++;
        foodViewModel.updateCart(id, amount);
    }

    private void minusAction(int id, int amount) {
        if (amount > 1) {
            amount--;
            foodViewModel.updateCart(id, amount);
        }
    }

//    private int pricing(CartContent cart) {
//        String temp = ((cart.getPrice().substring(0, cart.getPrice().length() - 1)));
//        String[] temp1 = temp.split(",");
//        return Integer.parseInt(temp1[0].concat(temp1[1])) * cart.getAmount();
//    }
}