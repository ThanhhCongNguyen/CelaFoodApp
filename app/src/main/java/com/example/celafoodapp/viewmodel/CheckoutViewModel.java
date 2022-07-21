package com.example.celafoodapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.repository.FoodRepository;
import com.example.celafoodapp.repository.local.entity.Order;

public class CheckoutViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private Order order;

    public CheckoutViewModel(Context context) {
        foodRepository = new FoodRepository(context);
    }

    public void insertOrder(Order order) {
        foodRepository.insertOrder(order);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
