package com.example.celafoodapp.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.celafoodapp.database.entity.Food;

import java.util.List;

@Dao
public interface FoodDao {
    @Query("SELECT * FROM food WHERE categoryTitle =:categoryTitle")
    LiveData<List<Food>> getFood(String categoryTitle);

    @Query("SELECT DISTINCT categoryTitle from food")
    LiveData<List<String>> getCategoryTitle();
}
