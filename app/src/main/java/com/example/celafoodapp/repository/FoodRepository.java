package com.example.celafoodapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.celafoodapp.repository.local.dao.CartDao;
import com.example.celafoodapp.repository.local.dao.FoodDao;
import com.example.celafoodapp.repository.local.dao.OrderDao;
import com.example.celafoodapp.repository.local.dao.UserDao;
import com.example.celafoodapp.repository.local.db.MyDatabase;
import com.example.celafoodapp.repository.local.entity.Cart;
import com.example.celafoodapp.repository.local.entity.CartContent;
import com.example.celafoodapp.repository.local.entity.Food;
import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.repository.local.entity.OrderContent;
import com.example.celafoodapp.repository.local.entity.User;

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

    public LiveData<List<Food>> getAllFood() {
        return foodDao.getAllFood();
    }

    public LiveData<List<String>> getCategoryTitle() {
        return foodDao.getCategoryTitle();
    }

    public LiveData<List<OrderContent>> getAllOrder(String userId) {
        return orderDao.getAllOrder(userId);
    }

    public LiveData<List<CartContent>> getCart(String userId) {
        return cartDao.getAllCart(userId);
    }

    public LiveData<User> getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    public LiveData<User> getUserById(String userId) {
        return userDao.getUserById(userId);
    }

    public void insertCart(Cart cart) {
        cartDao.insert(cart);
    }

    public void update(Cart cart) {
        cartDao.updateCart(cart);

    }

    public void insertOrder(Order order) {
        orderDao.insert(order);
    }

    public void insertMultipleRows(List<Order> orders) {
        orderDao.insertMultipleRows(orders);
    }

    public void insertUser(User user) {
        userDao.insertUser(user);
    }

    public void updatePassword(String userId, String password) {
        userDao.updatePassword(userId, password);
    }

    public void deleteCart(Cart cart) {
        cartDao.delete(cart);
    }

    public void updateCart(String id, int amount, String userId) {
        cartDao.update(id, amount, userId);
    }
}
