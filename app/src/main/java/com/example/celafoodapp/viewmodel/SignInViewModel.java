package com.example.celafoodapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.repository.FoodRepository;
import com.example.celafoodapp.repository.local.entity.User;

public class SignInViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private String userId;

    public SignInViewModel(Context context) {
        foodRepository = new FoodRepository(context);
    }

    public LiveData<User> getUser(String email, String password) {
        return foodRepository.getUser(email, password);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
