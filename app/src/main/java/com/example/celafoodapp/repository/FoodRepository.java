package com.example.celafoodapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.celafoodapp.local.dao.CartDao;
import com.example.celafoodapp.local.dao.FoodDao;
import com.example.celafoodapp.local.dao.OrderDao;
import com.example.celafoodapp.local.dao.UserDao;
import com.example.celafoodapp.local.db.MyDatabase;
import com.example.celafoodapp.local.entity.Cart;
import com.example.celafoodapp.local.entity.CartContent;
import com.example.celafoodapp.local.entity.Food;
import com.example.celafoodapp.local.entity.Order;
import com.example.celafoodapp.local.entity.OrderContent;

import java.util.List;

public class FoodRepository {
    private FoodDao foodDao;
    private CartDao cartDao;
    private OrderDao orderDao;
    private UserDao userDao;

    public FoodRepository(Context context) {
        MyDatabase myDatabase = MyDatabase.getInstance(context);
        foodDao = myDatabase.foodDao();
        cartDao = myDatabase.cartDao();
        orderDao = myDatabase.orderDao();
        userDao = myDatabase.userDao();
    }

    public LiveData<List<Food>> getFood(String categoryTitle) {
        return foodDao.getFood(categoryTitle);
    }

    public LiveData<List<String>> getCategoryTitle() {
        return foodDao.getCategoryTitle();
    }

    public LiveData<List<OrderContent>> getAllOrder() {
        return orderDao.getAllOrder();
    }

    public LiveData<List<CartContent>> getCart() {
        return cartDao.getAllCart();
    }

    public void insertCart(Cart cart) {
        cartDao.insert(cart);
    }

    public void insertOrder(Order order) {
        orderDao.insert(order);
    }

    public void insertMultipleRows(List<Order> orders) {
        orderDao.insertMultipleRows(orders);
    }

    public void deleteCart(Cart cart) {
        cartDao.delete(cart);
    }

    public void updateCart(int id, int amount) {
        cartDao.update(id, amount);
    }
}
