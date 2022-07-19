package com.example.celafoodapp.repository.local.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order")
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int userId;
    private int foodId;
    private int amount;

    public Order(int userId, int foodId, int amount) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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
