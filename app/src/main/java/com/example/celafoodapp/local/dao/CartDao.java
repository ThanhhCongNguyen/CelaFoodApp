package com.example.celafoodapp.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.celafoodapp.local.entity.Cart;
import com.example.celafoodapp.local.entity.CartContent;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart, food WHERE cart.foodId = food.id")
    LiveData<List<CartContent>> getAllCart();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("UPDATE cart SET amount = :amount WHERE id = :id")
    void update(int id, int amount);
}
