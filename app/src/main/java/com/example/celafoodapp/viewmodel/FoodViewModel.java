package com.example.celafoodapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.repository.local.entity.Cart;
import com.example.celafoodapp.repository.local.entity.CartContent;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.repository.local.entity.OrderContent;
import com.example.celafoodapp.repository.FoodRepository;

import java.util.ArrayList;
import java.util.List;

public class FoodViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private int totalItems = 1;
    private int totalPrice = 0;
    private List<CartContent> cartContents;
    private List<Order> orderContents;
    private List<Food> foods;

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

    public LiveData<List<CartContent>> getAllCart() {
        return foodRepository.getCart();
    }

    public LiveData<List<OrderContent>> getAllOrder() {
        return foodRepository.getAllOrder();
    }

    public void insertCart(Cart cart) {
        foodRepository.insertCart(cart);
    }

    public void update(Cart cart) {
        foodRepository.update(cart);
    }

    public void deleteCart(Cart cart) {
        foodRepository.deleteCart(cart);
    }

    public void updateCart(int id, int amount) {
        foodRepository.updateCart(id, amount);
    }

    public void insertOrder(Order order) {
        foodRepository.insertOrder(order);
    }

    public void insertMultipleRows(List<Order> orders) {
        foodRepository.insertMultipleRows(orders);
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

    public List<CartContent> getCartContents() {
        return cartContents;
    }

    public void setCartContents(List<CartContent> cartContents) {
        this.cartContents = cartContents;
    }

    public List<Order> getOrderContents() {
        return orderContents;
    }

    public void setOrderContents(List<Order> orderContents) {
        this.orderContents = orderContents;
    }

    public void addOrder(Order order) {
        orderContents.add(order);
    }
}
