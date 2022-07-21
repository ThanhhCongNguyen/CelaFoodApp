package com.example.celafoodapp.repository.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "order")
public class Order implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private int foodId;
    private int amount;

    public Order(String userId, int foodId, int amount) {
        this.userId = userId;
        this.foodId = foodId;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
