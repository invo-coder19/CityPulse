package com.example.cityhealth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    private Button btnBackHome;
    private TextView tvDashboardTitle, tvCityNameDash, tvHealthIndex, tvAQI, tvAQIStatus;
    private TextView tvGreenCover, tvUrbanHeat, tvWaterRisk, tvHealthcare;
    private City selectedCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Initialize views
        btnBackHome = findViewById(R.id.btnBackHome);
        tvDashboardTitle = findViewById(R.id.tvDashboardTitle);
        tvCityNameDash = findViewById(R.id.tvCityNameDash);
        tvHealthIndex = findViewById(R.id.tvHealthIndex);
        tvAQI = findViewById(R.id.tvAQI);
        tvAQIStatus = findViewById(R.id.tvAQIStatus);
        tvGreenCover = findViewById(R.id.tvGreenCover);
        tvUrbanHeat = findViewById(R.id.tvUrbanHeat);
        tvWaterRisk = findViewById(R.id.tvWaterRisk);
        tvHealthcare = findViewById(R.id.tvHealthcare);

        // Get city data from intent
        Intent intent = getIntent();
        if (intent.hasExtra("CITY_DATA")) {
            selectedCity = (City) intent.getSerializableExtra("CITY_DATA");
            displayCityData();
        }

        // Back to home button
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void displayCityData() {
        if (selectedCity != null) {
            tvCityNameDash.setText(selectedCity.getCityName() + ", " + selectedCity.getState());

            // Health Index
            tvHealthIndex.setText(String.format("%.1f / 100", selectedCity.getHealthIndex()));
            tvHealthIndex.setTextColor(getHealthColor(selectedCity.getHealthIndex()));

            // AQI
            tvAQI.setText(String.format("%.0f", selectedCity.getAqi()));
            tvAQIStatus.setText(selectedCity.getAqiStatus());
            tvAQI.setTextColor(Color.parseColor(selectedCity.getAqiColor()));
            tvAQIStatus.setTextColor(Color.parseColor(selectedCity.getAqiColor()));

            // Green Cover
            tvGreenCover.setText(String.format("%.1f%%", selectedCity.getGreenCover()));

            // Urban Heat Island
            tvUrbanHeat.setText(String.format("%.1f°C", selectedCity.getUrbanHeatIsland()));

            // Water & Flood Risk
            tvWaterRisk.setText(String.format("%.1f%%", selectedCity.getWaterFloodRisk()));

            // Healthcare Facilities
            tvHealthcare.setText(String.valueOf(selectedCity.getHealthcareFacilities()) + " facilities");
        }
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private int getHealthColor(double healthIndex) {
        if (healthIndex >= 80) return Color.parseColor("#27ae60");
        else if (healthIndex >= 60) return Color.parseColor("#2ecc71");
        else if (healthIndex >= 40) return Color.parseColor("#f39c12");
        else if (healthIndex >= 20) return Color.parseColor("#e67e22");
        else return Color.parseColor("#e74c3c");
    }

}

