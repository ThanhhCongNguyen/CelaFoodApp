package com.example.celafoodapp.util;

public interface AppData {
    interface Key {
        String item = "keyItem";
        String price = "keyPrice";
        String food = "keyFood";
        String foodId = "foodId";
        String fartContents = "keyCartContents";
        String mySharedPreferences = "mySharedPreferences";
        String isRemember = "isRemember";
        String email = "email";
        String password = "password";
    }

    interface Config {
        int SPAN_COUNT = 2;
        String CATEGORY_DEFAULT = "Cơm Eat Clean";
    }
}
