package com.example.celafoodapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.celafoodapp.database.entity.Cart;
import com.example.celafoodapp.database.entity.CartContent;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT cart.id, userId, foodName, image, descriptionEN, price, image, amount FROM cart, food WHERE cart.foodId = food.id")
    LiveData<List<CartContent>> getAllCart();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);

    @Delete
    void delete(Cart cart);

    @Query("UPDATE cart SET amount = :amount WHERE id = :id")
    void update(int id, int amount);
}
