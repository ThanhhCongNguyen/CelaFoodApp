package com.example.celafoodapp.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.celafoodapp.util.Toolkit;

public abstract class BaseActivity extends AppCompatActivity {
    protected Toolkit toolkit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolkit = new Toolkit(BaseActivity.this);
    }
}
