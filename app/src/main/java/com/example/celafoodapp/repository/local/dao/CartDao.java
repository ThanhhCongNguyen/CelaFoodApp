package com.example.celafoodapp.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.celafoodapp.repository.local.entity.Cart;
import com.example.celafoodapp.repository.local.entity.CartContent;

import java.util.List;

@Dao
public interface CartDao {
    @Query("SELECT * FROM cart, food WHERE cart.foodId = food.id")
    LiveData<List<CartContent>> getAllCart();

    @Query("UPDATE cart SET amount = :amount WHERE id = :id")
    void update(int id, int amount);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);

    @Update (entity = Cart.class)
    void updateCart(Cart cart);

    @Delete
    void delete(Cart cart);
}
