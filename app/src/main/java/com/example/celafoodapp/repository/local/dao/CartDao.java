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
    @Query("SELECT cart.id, cart.userId, cart.foodId, foodName, descriptionEN, descriptionVN, price, image, amount " +
            "FROM cart, food " +
            "WHERE cart.foodId = food.id AND cart.userId = :userId")
    LiveData<List<CartContent>> getAllCart(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Cart cart);

    @Query("UPDATE cart SET amount = :amount WHERE id = :id AND cart.userId = :userId")
    void update(String id, int amount, String userId);

    @Update
    void updateCart(Cart cart);

    @Delete
    void delete(Cart cart);
}
