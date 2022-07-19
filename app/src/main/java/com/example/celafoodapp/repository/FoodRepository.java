package com.example.celafoodapp.repository;

import android.content.Context;
import android.util.Log;

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

    public LiveData<List<OrderContent>> getAllOrder() {
        return orderDao.getAllOrder();
    }

    public LiveData<List<CartContent>> getCart() {
        return cartDao.getAllCart();
    }

    public LiveData<User> getUser(String email, String password) {
        return userDao.getUser(email, password);
    }

    public void insertCart(Cart cart) {
        cartDao.insert(cart);
        Log.d("tag", "insert");
    }

    public void update(Cart cart) {
        cartDao.updateCart(cart);
        Log.d("tag", "update");
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

    public void deleteCart(Cart cart) {
        cartDao.delete(cart);
        Log.d("tag", "delete");
    }

    public void updateCart(int id, int amount) {
        cartDao.update(id, amount);
    }
}
