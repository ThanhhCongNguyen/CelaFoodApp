package com.example.celafoodapp.util;

import android.content.Context;
import android.widget.Toast;

import com.example.celafoodapp.repository.local.entity.CartContent;

public class Utility {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static int pricing(CartContent cart) {
        String temp = ((cart.getPrice().substring(0, cart.getPrice().length() - 1)));
        String[] temp1 = temp.split(",");
        return Integer.parseInt(temp1[0].concat(temp1[1])) * cart.getAmount();
    }
}
