package com.example.celafoodapp.database.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.celafoodapp.database.dao.CartDao;
import com.example.celafoodapp.database.entity.Cart;
import com.example.celafoodapp.database.entity.Category;
import com.example.celafoodapp.database.entity.Food;
import com.example.celafoodapp.database.dao.FoodDao;

@Database(entities = {Category.class, Food.class, Cart.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    public abstract CartDao cartDao();

    private static MyDatabase INSTANCE;

    public static synchronized MyDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    MyDatabase.class,
                    "cela_food")
                    .createFromAsset("cela_food.db").build();
        }
        return INSTANCE;
    }
}
