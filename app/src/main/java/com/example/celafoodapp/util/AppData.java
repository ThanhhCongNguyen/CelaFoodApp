package com.example.celafoodapp.util;

public interface AppData {
    interface Key {
        String keyItem = "keyItem";
        String keyPrice = "keyPrice";
        String keyFood = "keyFood";
    }

    interface Config {
        int SPAN_COUNT = 2;
        String CATEGORY_DEFAULT = "Cơm Eat Clean";
    }
}
