package com.example.celafoodapp.repository.local.entity;

public class CartContent {
    private int id;
    private int userId;
    private int foodId;
    private String foodName;
    private String descriptionEN;
    private String descriptionVN;
    private String price;
    private String image;
    private int amount;

    public CartContent(int id, int userId, int foodId, String foodName, String descriptionEN, String descriptionVN, String price, String image, int amount) {
        this.id = id;
        this.userId = userId;
        this.foodId = foodId;
        this.foodName = foodName;
        this.descriptionEN = descriptionEN;
        this.descriptionVN = descriptionVN;
        this.price = price;
        this.image = image;
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

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getIngredient() {
        return descriptionEN;
    }

    public void setIngredient(String ingredient) {
        this.descriptionEN = ingredient;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getDescriptionEN() {
        return descriptionEN;
    }

    public void setDescriptionEN(String descriptionEN) {
        this.descriptionEN = descriptionEN;
    }

    public String getDescriptionVN() {
        return descriptionVN;
    }

    public void setDescriptionVN(String descriptionVN) {
        this.descriptionVN = descriptionVN;
    }

    @Override
    public String toString() {
        return "CartContent{" +
                "id=" + id +
                ", userId=" + userId +
                ", foodName='" + foodName + '\'' +
                ", ingredient='" + descriptionEN + '\'' +
                ", price='" + price + '\'' +
                ", image='" + image + '\'' +
                ", amount=" + amount +
                '}';
    }
}
