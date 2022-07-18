package com.example.celafoodapp.local.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.celafoodapp.local.dao.CartDao;
import com.example.celafoodapp.local.dao.OrderDao;
import com.example.celafoodapp.local.dao.UserDao;
import com.example.celafoodapp.local.entity.Cart;
import com.example.celafoodapp.local.entity.Category;
import com.example.celafoodapp.local.entity.Food;
import com.example.celafoodapp.local.dao.FoodDao;
import com.example.celafoodapp.local.entity.Order;
import com.example.celafoodapp.local.entity.User;

@Database(entities = {Category.class,
                          Food.class,
                          Cart.class,
                         Order.class,
                         User.class},
                         version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract FoodDao foodDao();

    public abstract CartDao cartDao();

    public abstract OrderDao orderDao();

    public abstract UserDao userDao();

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
