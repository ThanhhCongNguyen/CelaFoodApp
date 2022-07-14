package com.example.celafoodapp.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.celafoodapp.util.Toolkit;

public abstract class BaseFragment extends Fragment {
    protected Toolkit toolkit;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toolkit = new Toolkit((BaseActivity) getContext());
    }

}
