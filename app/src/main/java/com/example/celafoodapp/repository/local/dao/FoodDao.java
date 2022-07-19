package com.example.celafoodapp.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.celafoodapp.repository.local.entity.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food WHERE categoryTitle =:categoryTitle")
    LiveData<List<Food>> getFood(String categoryTitle);

    @Query("SELECT DISTINCT categoryTitle from food")
    LiveData<List<String>> getCategoryTitle();

    @Query("SELECT * FROM food")
    LiveData<List<Food>> getAllFood();
}
