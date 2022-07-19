package com.example.celafoodapp.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.celafoodapp.repository.local.entity.Order;
import com.example.celafoodapp.repository.local.entity.OrderContent;

import java.util.List;

@Dao
public interface OrderDao {
    @Query("SELECT * FROM `order`, food WHERE `order`.foodId = food.id")
    LiveData<List<OrderContent>> getAllOrder();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Order order);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMultipleRows(List<Order> orders);
}
