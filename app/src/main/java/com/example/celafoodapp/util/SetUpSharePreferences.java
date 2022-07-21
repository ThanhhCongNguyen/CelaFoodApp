package com.example.celafoodapp.util;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.celafoodapp.ui.activity.MainActivity;

public class SetUpSharePreferences {
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static SharedPreferences initSharePreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AppData.Key.mySharedPreferences, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void setUpSharePreferences(boolean isRemember, String userId) {
        editor = sharedPreferences.edit();
        editor.putBoolean(AppData.Key.isRemember, isRemember);
        editor.putString(AppData.Key.userId, userId);
        editor.apply();
    }

    public void setUpLogout(boolean isRemember) {
        editor = sharedPreferences.edit();
        editor.putBoolean(AppData.Key.isRemember, isRemember);
        editor.apply();
    }

    public void getData(Context context) {
        boolean isRemember = sharedPreferences.getBoolean(AppData.Key.isRemember, false);
        if (isRemember == true) {
            context.startActivity(new Intent(context, MainActivity.class));
        }
    }

    public static String getUserId() {
        String userId = sharedPreferences.getString(AppData.Key.userId, "");
        return userId;
    }
}
