package com.example.celafoodapp.local.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "food")
public class Food implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String foodName;
    private String price;
    private String image;
    private String descriptionVN;
    private String descriptionEN;
    private String categoryTitle;
    @Ignore
    private int amount;
    @Ignore
    private int cartId;

    public Food(int id, String foodName, String price, String image, String descriptionVN, String descriptionEN, String categoryTitle) {
        this.id = id;
        this.foodName = foodName;
        this.price = price;
        this.image = image;
        this.descriptionVN = descriptionVN;
        this.descriptionEN = descriptionEN;
        this.categoryTitle = categoryTitle;
        this.amount = 1;
    }

    @Ignore
    public Food(int id, String foodName, String price, String image, String descriptionVN, String descriptionEN, int amount) {
        this.id = id;
        this.foodName = foodName;
        this.price = price;
        this.image = image;
        this.descriptionVN = descriptionVN;
        this.descriptionEN = descriptionEN;
        this.amount = amount;
    }

    @Ignore
    public Food(int cartId, int foodId, String foodName, String price, String image, String descriptionVN, String descriptionEN, int amount) {
        this.cartId = cartId;
        this.id = foodId;
        this.foodName = foodName;
        this.price = price;
        this.image = image;
        this.descriptionVN = descriptionVN;
        this.descriptionEN = descriptionEN;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescriptionVN() {
        return descriptionVN;
    }

    public void setDescriptionVN(String descriptionVN) {
        this.descriptionVN = descriptionVN;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }
}
