package com.anesthesiacalculator.pro.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.airbnb.lottie.LottieAnimationView;
import com.anesthesiacalculator.pro.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 3000; // 3 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize Lottie animation
        LottieAnimationView animationView = findViewById(R.id.lottie_splash);
        TextView appTitle = findViewById(R.id.app_title);
        TextView appSubtitle = findViewById(R.id.app_subtitle);

        // Start animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, android.R.anim.fade_in);
        appTitle.startAnimation(fadeIn);
        appSubtitle.startAnimation(fadeIn);

        // Navigate to MainActivity after timeout
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }, SPLASH_TIMEOUT);
    }
}
