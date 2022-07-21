package com.example.celafoodapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.repository.FoodRepository;

public class UpdatePasswordViewModel extends ViewModel {
    private FoodRepository foodRepository;
    private String userId;

    public UpdatePasswordViewModel(Context context) {
        foodRepository = new FoodRepository(context);
    }

    public void updatePassword(String userId, String password) {
        foodRepository.updatePassword(userId, password);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
