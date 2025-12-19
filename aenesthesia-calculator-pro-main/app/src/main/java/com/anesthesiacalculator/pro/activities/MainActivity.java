package com.anesthesiacalculator.pro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.android.material.card.MaterialCardView;
import com.anesthesiacalculator.pro.R;
import com.anesthesiacalculator.pro.utils.ThemeManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private MaterialCardView cardCalculate, cardSavedPatients, cardSettings, cardAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Ensure theme is applied before setting content view
        ThemeManager.initialize(this);
        
        setContentView(R.layout.activity_main);

        initViews();
        animateCards();
    }

    private void initViews() {
        cardCalculate = findViewById(R.id.card_calculate);
        cardSavedPatients = findViewById(R.id.card_saved_patients);
        cardSettings = findViewById(R.id.card_settings);
        cardAbout = findViewById(R.id.card_about);

        cardCalculate.setOnClickListener(this);
        cardSavedPatients.setOnClickListener(this);
        cardSettings.setOnClickListener(this);
        cardAbout.setOnClickListener(this);
    }

    private void animateCards() {
        // A helper function to apply animation with a start offset
        Animation slideUpCalculate = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        cardCalculate.startAnimation(slideUpCalculate);

        Animation slideUpSavedPatients = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUpSavedPatients.setStartOffset(100);
        cardSavedPatients.startAnimation(slideUpSavedPatients);

        Animation slideUpSettings = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUpSettings.setStartOffset(200);
        cardSettings.startAnimation(slideUpSettings);

        Animation slideUpAbout = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        slideUpAbout.setStartOffset(300);
        cardAbout.startAnimation(slideUpAbout);
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        
        if (v.getId() == R.id.card_calculate) {
            intent = new Intent(this, PatientInputActivity.class);
        } else if (v.getId() == R.id.card_saved_patients) {
            intent = new Intent(this, SavedPatientsActivity.class);
        } else if (v.getId() == R.id.card_settings) {
            intent = new Intent(this, SettingsActivity.class);
        } else if (v.getId() == R.id.card_about) {
            intent = new Intent(this, AboutActivity.class);
        }

        if (intent != null) {
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }
    }
}
