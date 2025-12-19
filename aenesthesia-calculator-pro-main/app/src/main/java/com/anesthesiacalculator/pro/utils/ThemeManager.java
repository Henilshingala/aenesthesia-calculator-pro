package com.anesthesiacalculator.pro.utils;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

public class ThemeManager {
    private static final String PREFS_NAME = "theme_prefs";
    private static final String KEY_DARK_MODE = "dark_mode";
    private static SharedPreferences preferences;

    public static void initialize(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        applyTheme();
    }

    public static void setDarkMode(boolean isDarkMode) {
        preferences.edit().putBoolean(KEY_DARK_MODE, isDarkMode).apply();
        applyTheme();
    }

    public static boolean isDarkMode() {
        if (preferences == null) {
            return false; // Default to light mode if preferences not initialized
        }
        return preferences.getBoolean(KEY_DARK_MODE, false);
    }

    private static void applyTheme() {
        if (preferences == null) {
            return; // Prevent null pointer exception
        }
        
        if (isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}
