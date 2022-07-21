package com.example.celafoodapp.repository.local.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.celafoodapp.repository.local.entity.User;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user WHERE user.email = :email AND user.password = :password")
    LiveData<User> getUser(String email, String password);

    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<User> getUserById(String userId);

    @Insert
    void insertUser(User user);
}
