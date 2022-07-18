package com.example.celafoodapp.local.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.celafoodapp.local.entity.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE user.email = :email AND user.password = :password")
    void getUser(String email, String password);

    @Insert
    void insertUser(User user);
}
