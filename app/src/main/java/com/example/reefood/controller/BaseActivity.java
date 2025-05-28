package com.example.reefood.controller;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

public abstract class BaseActivity extends AppCompatActivity {
    protected static final String PREFS_NAME = "app_preferences";
    protected static final String KEY_DARK_MODE = "dark_mode";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        applySavedTheme(); // Aplicar antes del super.onCreate
        super.onCreate(savedInstanceState);
    }

    protected void applySavedTheme() {
        boolean isDarkMode = getDarkModePreference();
        AppCompatDelegate.setDefaultNightMode(
                isDarkMode ? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO
        );
    }

    protected boolean getDarkModePreference() {
        return getSharedPreferences().getBoolean(KEY_DARK_MODE, false);
    }

    protected void saveThemePreference(boolean isDarkMode) {
        getSharedPreferences().edit()
                .putBoolean(KEY_DARK_MODE, isDarkMode)
                .apply();
    }

    protected SharedPreferences getSharedPreferences() {
        return getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }
}
