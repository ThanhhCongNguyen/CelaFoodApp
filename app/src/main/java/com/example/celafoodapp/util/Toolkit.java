package com.example.celafoodapp.util;

import com.example.celafoodapp.ui.base.BaseActivity;

import java.util.Random;

public class Toolkit {
    public Random random;
    public AppLogger appLogger;

    public Toolkit(BaseActivity baseActivity) {
        this.random = new Random();
        this.appLogger = AppLogger.getInstance();
    }
}
