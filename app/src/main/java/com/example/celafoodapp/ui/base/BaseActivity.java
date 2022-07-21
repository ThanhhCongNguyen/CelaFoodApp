package com.example.celafoodapp.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.celafoodapp.util.Executor;

import java.util.concurrent.ExecutorService;

public abstract class BaseActivity extends AppCompatActivity {
    protected ExecutorService executor;

    public BaseActivity() {
        this.executor = Executor.getInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
