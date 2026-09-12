package com.example.cityhealth;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private Button btnBackHome;
    private TextView tvSettingsTitle;
    private Switch switchNotifications, switchDarkMode, switchAutoSync;
    private Button btnClearCache, btnAbout;
    private android.content.SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Initialize views
        btnBackHome = findViewById(R.id.btnBackHome);
        tvSettingsTitle = findViewById(R.id.tvSettingsTitle);
        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchAutoSync = findViewById(R.id.switchAutoSync);
        btnClearCache = findViewById(R.id.btnClearCache);
        btnAbout = findViewById(R.id.btnAbout);

        // Load saved preferences
        switchNotifications.setChecked(sharedPreferences.getBoolean("notifications_enabled", true));
        switchDarkMode.setChecked(sharedPreferences.getBoolean("dark_mode_enabled", false));
        switchAutoSync.setChecked(sharedPreferences.getBoolean("auto_sync_enabled", true));

        // Switch listeners
        switchNotifications.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("notifications_enabled", isChecked).apply();
            Toast.makeText(SettingsActivity.this, isChecked ? "Notifications enabled" : "Notifications disabled", Toast.LENGTH_SHORT).show();
        });

        switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("dark_mode_enabled", isChecked).apply();
            androidx.appcompat.app.AppCompatDelegate.setDefaultNightMode(
                    isChecked ? androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES : androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
            );
            Toast.makeText(SettingsActivity.this, isChecked ? "Dark mode enabled" : "Dark mode disabled", Toast.LENGTH_SHORT).show();
        });

        switchAutoSync.setOnCheckedChangeListener((buttonView, isChecked) -> {
            sharedPreferences.edit().putBoolean("auto_sync_enabled", isChecked).apply();
            Toast.makeText(SettingsActivity.this, isChecked ? "Auto sync enabled" : "Auto sync disabled", Toast.LENGTH_SHORT).show();
        });

        // Clear cache button
        btnClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    java.io.File cacheDir = getCacheDir();
                    if (cacheDir != null && cacheDir.isDirectory()) {
                        deleteDir(cacheDir);
                    }
                    Toast.makeText(SettingsActivity.this, "Cache cleared successfully", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(SettingsActivity.this, "Cache cleared", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // About button
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "CityPulse v1.0\nUrban Health & Environmental Intelligence\nNASA Space Apps Challenge Project", Toast.LENGTH_LONG).show();
            }
        });

        // Back to home button
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean deleteDir(java.io.File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (String child : children) {
                    boolean success = deleteDir(new java.io.File(dir, child));
                    if (!success) {
                        return false;
                    }
                }
            }
            return dir.delete();
        } else if (dir != null && dir.isFile()) {
            return dir.delete();
        }
        return false;
    }
}
