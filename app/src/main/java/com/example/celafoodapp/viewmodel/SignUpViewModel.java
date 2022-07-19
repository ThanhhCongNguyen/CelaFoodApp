package com.example.celafoodapp.viewmodel;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.example.celafoodapp.repository.FoodRepository;
import com.example.celafoodapp.repository.local.entity.User;

public class SignUpViewModel extends ViewModel {
    private FoodRepository foodRepository;

    public SignUpViewModel(Context context) {
        foodRepository = new FoodRepository(context);
    }

    public void insertUser(User user) {
        foodRepository.insertUser(user);
    }
}
