package com.anesthesiacalculator.pro.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.utils.ThemeManager;

public class SettingsActivity extends AppCompatActivity {
    private SwitchMaterial switchDarkMode;
    private AutoCompleteTextView spinnerLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Ensure theme is applied before setting content view
        ThemeManager.initialize(this);
        
        setContentView(R.layout.activity_settings);

        initViews();
        setupLanguageSpinner();
        loadSettings();
    }

    private void initViews() {
        switchDarkMode = findViewById(R.id.switch_dark_mode);
        spinnerLanguage = findViewById(R.id.spinner_language);

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // Only recreate if the theme has actually changed
            if (ThemeManager.isDarkMode() != isChecked) {
                ThemeManager.setDarkMode(isChecked);
                recreate(); // Restart activity to apply theme
            }
        });
    }

    private void setupLanguageSpinner() {
        String[] languages = {"English", "Spanish", "French", "German"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, 
            android.R.layout.simple_dropdown_item_1line, languages);
        spinnerLanguage.setAdapter(adapter);
    }

    private void loadSettings() {
        switchDarkMode.setChecked(ThemeManager.isDarkMode());
    }
}
