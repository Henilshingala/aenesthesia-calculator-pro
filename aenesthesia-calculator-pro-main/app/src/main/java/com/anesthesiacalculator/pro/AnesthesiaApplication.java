package com.anesthesiacalculator.pro;

import android.app.Application;
import com.anesthesiacalculator.pro.utils.ThemeManager;

public class AnesthesiaApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ThemeManager.initialize(this);
    }
}