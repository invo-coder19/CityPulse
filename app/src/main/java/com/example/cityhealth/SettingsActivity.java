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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        // Initialize views
        btnBackHome = findViewById(R.id.btnBackHome);
        tvSettingsTitle = findViewById(R.id.tvSettingsTitle);
        switchNotifications = findViewById(R.id.switchNotifications);
        switchDarkMode = findViewById(R.id.switchDarkMode);
        switchAutoSync = findViewById(R.id.switchAutoSync);
        btnClearCache = findViewById(R.id.btnClearCache);
        btnAbout = findViewById(R.id.btnAbout);

        // Switch listeners
        switchNotifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchNotifications.isChecked()) {
                    Toast.makeText(SettingsActivity.this, "Notifications enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Notifications disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchDarkMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchDarkMode.isChecked()) {
                    Toast.makeText(SettingsActivity.this, "Dark mode enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Dark mode disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        switchAutoSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (switchAutoSync.isChecked()) {
                    Toast.makeText(SettingsActivity.this, "Auto sync enabled", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SettingsActivity.this, "Auto sync disabled", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Clear cache button
        btnClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "Cache cleared successfully", Toast.LENGTH_SHORT).show();
            }
        });

        // About button
        btnAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SettingsActivity.this, "City Health App v1.0\nDeveloped for city comparison", Toast.LENGTH_LONG).show();
            }
        });

        // Back to home button
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
