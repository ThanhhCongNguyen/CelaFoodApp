package com.example.celafoodapp.util;

public interface AppData {
    interface Key {
        String item = "keyItem";
        String price = "keyPrice";
        String totalPrice = "totalPrice";
        String food = "keyFood";
        String foodId = "foodId";
        String mySharedPreferences = "mySharedPreferences";
        String isRemember = "isRemember";
        String userId = "userId";
        String orderObject = "orderObject";
    }

    interface Config {
        int SPAN_COUNT = 2;
        int DELAY_MILLIS = 2000;
        String CATEGORY_DEFAULT = "Cơm Eat Clean";
    }
}
