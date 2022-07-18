package com.example.celafoodapp.util;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.celafoodapp.local.entity.CartContent;
import com.google.android.material.snackbar.Snackbar;

public class Utility {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void snackbar(RecyclerView recyclerView, String message){
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    public static int pricing(CartContent cart) {
        String temp = ((cart.getPrice().substring(0, cart.getPrice().length() - 1)));
        String[] temp1 = temp.split(",");
        return Integer.parseInt(temp1[0].concat(temp1[1])) * cart.getAmount();
    }
}
