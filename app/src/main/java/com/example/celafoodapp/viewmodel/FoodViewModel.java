package com.example.celafoodapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.database.entity.Cart;
import com.example.celafoodapp.database.entity.CartContent;
import com.example.celafoodapp.database.entity.Food;
import com.example.celafoodapp.repository.FoodRepository;

import java.util.List;

public class FoodViewModel extends ViewModel {
    private FoodRepository foodRepository;

    public FoodViewModel(Context context) {
        foodRepository = new FoodRepository(context);
    }

    public LiveData<List<Food>> getFood(String categoryTitle) {
        return foodRepository.getFood(categoryTitle);
    }

    public LiveData<List<String>> getCategoryTitle() {
        return foodRepository.getCategoryTitle();
    }

    public LiveData<List<CartContent>> getAllCart() {
        return foodRepository.getCart();
    }

    public void insertCart(Cart cart) {
        foodRepository.insertCart(cart);
    }

    public void deleteCart(Cart cart) {
        foodRepository.deleteCart(cart);
    }

    public void updateCart(int id, int amount) {
        foodRepository.updateCart(id, amount);
    }
}
