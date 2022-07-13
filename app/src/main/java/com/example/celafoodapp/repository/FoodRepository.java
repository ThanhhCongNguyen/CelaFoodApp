package com.example.celafoodapp.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.celafoodapp.database.dao.CartDao;
import com.example.celafoodapp.database.dao.FoodDao;
import com.example.celafoodapp.database.db.MyDatabase;
import com.example.celafoodapp.database.entity.Cart;
import com.example.celafoodapp.database.entity.CartContent;
import com.example.celafoodapp.database.entity.Food;

import java.util.List;

public class FoodRepository {
    private FoodDao foodDao;
    private CartDao cartDao;

    public FoodRepository(Context context) {
        MyDatabase myDatabase = MyDatabase.getInstance(context);
        foodDao = myDatabase.foodDao();
        cartDao = myDatabase.cartDao();
    }

    public LiveData<List<Food>> getFood(String categoryTitle) {
        return foodDao.getFood(categoryTitle);
    }

    public LiveData<List<String>> getCategoryTitle() {
        return foodDao.getCategoryTitle();
    }

    public LiveData<List<CartContent>> getCart() {
        return cartDao.getAllCart();
    }

    public void insertCart(Cart cart) {
        new Thread(() -> cartDao.insert(cart)).start();
    }

    public void deleteCart(Cart cart) {
        new Thread(() -> cartDao.delete(cart)).start();
    }

    public void updateCart(int id, int amount) {
        new Thread(() -> cartDao.update(id, amount)).start();
    }
}
