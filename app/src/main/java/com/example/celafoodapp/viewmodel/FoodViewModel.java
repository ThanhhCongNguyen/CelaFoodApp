package com.example.celafoodapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.repository.FoodRepository;
import com.example.celafoodapp.repository.local.entity.Cart;
import com.example.celafoodapp.repository.local.entity.CartContent;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.repository.local.entity.OrderContent;
import com.example.celafoodapp.repository.local.entity.User;

import java.util.ArrayList;
import java.util.List;

public class FoodViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private int totalItems = 1;
    private int totalPrice = 0;
    private List<CartContent> cartContents;
    private List<Order> orderContents;
    private List<Food> foods;
    private String userId;
    private Food food;

    public FoodViewModel(Context context) {
        foodRepository = new FoodRepository(context);
        cartContents = new ArrayList<>();
        orderContents = new ArrayList<>();

    }

    public LiveData<List<Food>> getFood(String categoryTitle) {
        return foodRepository.getFood(categoryTitle);
    }

    public LiveData<List<Food>> getAllFood() {
        return foodRepository.getAllFood();
    }

    public LiveData<List<String>> getCategoryTitle() {
        return foodRepository.getCategoryTitle();
    }

    public LiveData<List<CartContent>> getAllCart(String userId) {
        return foodRepository.getCart(userId);
    }

    public LiveData<List<OrderContent>> getAllOrder(String userId) {
        return foodRepository.getAllOrder(userId);
    }

    public LiveData<User> getUserById(String userId) {
        return foodRepository.getUserById(userId);
    }

    public void insertCart(Cart cart) {
        foodRepository.insertCart(cart);
    }

    public void updateCart(String id, int amount, String userId) {
        foodRepository.updateCart(id, amount, userId);
    }

    public void deleteCart(Cart cart) {
        foodRepository.deleteCart(cart);
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }

    public void plusItems() {
        this.totalItems++;
    }

    public void minusItems() {
        if (this.totalItems > 1) {
            this.totalItems--;
        } else {
            return;
        }
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setCartContents(List<CartContent> cartContents) {
        this.cartContents = cartContents;
    }
}
