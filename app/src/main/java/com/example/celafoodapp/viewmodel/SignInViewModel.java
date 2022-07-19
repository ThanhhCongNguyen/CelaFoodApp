package com.example.celafoodapp.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.repository.FoodRepository;
import com.example.celafoodapp.repository.local.entity.User;
import com.example.celafoodapp.ui.activity.MainActivity;
import com.example.celafoodapp.util.AppData;

public class SignInViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private static SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SignInViewModel(Context context) {
        foodRepository = new FoodRepository(context);
    }

    public LiveData<User> getUser(String email, String password) {
        return foodRepository.getUser(email, password);
    }

    public static SharedPreferences initSharePreferences(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(AppData.Key.mySharedPreferences, Context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }

    public void setUpSharePreferences(boolean isRemember) {
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
}
