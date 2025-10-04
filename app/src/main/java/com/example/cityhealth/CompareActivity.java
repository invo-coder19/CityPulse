package com.example.cityhealth;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CompareActivity extends AppCompatActivity {

    private static final String TAG = "CompareActivity";
    private Button btnBackHome, btnCompare;
    private Spinner spinnerCity1, spinnerCity2;
    private LinearLayout layoutComparison;
    private TextView tvCity1Name, tvCity2Name, tvWinner;
    private TextView tvCity1Health, tvCity2Health, tvCity1AQI, tvCity2AQI;
    private TextView tvCity1Green, tvCity2Green, tvCity1Heat, tvCity2Heat;
    private TextView tvCity1Healthcare, tvCity2Healthcare;

    private List<City> cityList;
    private City selectedCity1, selectedCity2;
    private List<String> cityNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Log.d(TAG, "onCreate started");

        // Initialize views
        spinnerCity1 = findViewById(R.id.spinnerCity1);
        spinnerCity2 = findViewById(R.id.spinnerCity2);
        btnCompare = findViewById(R.id.btnCompare);
        btnBackHome = findViewById(R.id.btnBackHome);
        layoutComparison = findViewById(R.id.layoutComparison);

        tvCity1Name = findViewById(R.id.tvCity1Name);
        tvCity2Name = findViewById(R.id.tvCity2Name);
        tvWinner = findViewById(R.id.tvWinner);

        tvCity1Health = findViewById(R.id.tvCity1Health);
        tvCity2Health = findViewById(R.id.tvCity2Health);
        tvCity1AQI = findViewById(R.id.tvCity1AQI);
        tvCity2AQI = findViewById(R.id.tvCity2AQI);
        tvCity1Green = findViewById(R.id.tvCity1Green);
        tvCity2Green = findViewById(R.id.tvCity2Green);
        tvCity1Heat = findViewById(R.id.tvCity1Heat);
        tvCity2Heat = findViewById(R.id.tvCity2Heat);
        tvCity1Healthcare = findViewById(R.id.tvCity1Healthcare);
        tvCity2Healthcare = findViewById(R.id.tvCity2Healthcare);

        Log.d(TAG, "Views initialized");

        // Load city data
        loadCityData();

        // Setup spinners
        setupSpinners();

        // Initially hide comparison layout
        layoutComparison.setVisibility(View.GONE);

        Log.d(TAG, "Setting up button listeners");

        // Compare button click listener
        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Compare button clicked!");
                Toast.makeText(CompareActivity.this, "Button clicked!", Toast.LENGTH_SHORT).show();

                if (selectedCity1 == null || selectedCity2 == null) {
                    Toast.makeText(CompareActivity.this, "Please select 2 cities to compare", Toast.LENGTH_LONG).show();
                    Log.d(TAG, "City1: " + selectedCity1 + ", City2: " + selectedCity2);
                    return;
                }

                if (selectedCity1.getCityName().equals(selectedCity2.getCityName())) {
                    Toast.makeText(CompareActivity.this, "Please select different cities", Toast.LENGTH_LONG).show();
                    return;
                }

                Log.d(TAG, "Performing comparison");
                performComparison();
            }
        });

        // Back to home button
        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Back button clicked");
                Intent intent = new Intent(CompareActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Log.d(TAG, "onCreate completed");
    }

    private void loadCityData() {
        Log.d(TAG, "Loading city data");
        CSVReader csvReader = new CSVReader(this);

        try {
            cityList = csvReader.readCitiesFromCSV("cities_data.csv");
            if (cityList.isEmpty()) {
                cityList = csvReader.getSampleCities();
            }
        } catch (Exception e) {
            cityList = csvReader.getSampleCities();
            Log.e(TAG, "Error loading CSV: " + e.getMessage());
        }

        Log.d(TAG, "Loaded " + cityList.size() + " cities");

        // Create list of city names for spinner
        cityNames = new ArrayList<>();
        cityNames.add("Select City");
        for (City city : cityList) {
            cityNames.add(city.getCityName());
        }
    }

    private void setupSpinners() {
        Log.d(TAG, "Setting up spinners");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, cityNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerCity1.setAdapter(adapter);
        spinnerCity2.setAdapter(adapter);

        spinnerCity1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Spinner1 selected position: " + position);
                if (position > 0) {
                    selectedCity1 = cityList.get(position - 1);
                    Log.d(TAG, "Selected City1: " + selectedCity1.getCityName());
                } else {
                    selectedCity1 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity1 = null;
            }
        });

        spinnerCity2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Spinner2 selected position: " + position);
                if (position > 0) {
                    selectedCity2 = cityList.get(position - 1);
                    Log.d(TAG, "Selected City2: " + selectedCity2.getCityName());
                } else {
                    selectedCity2 = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedCity2 = null;
            }
        });

        Log.d(TAG, "Spinners setup complete");
    }

    private void performComparison() {
        Log.d(TAG, "Starting comparison");
        layoutComparison.setVisibility(View.VISIBLE);

        // Set city names
        tvCity1Name.setText(selectedCity1.getCityName());
        tvCity2Name.setText(selectedCity2.getCityName());

        // Compare Health Index
        tvCity1Health.setText(String.format("%.1f", selectedCity1.getHealthIndex()));
        tvCity2Health.setText(String.format("%.1f", selectedCity2.getHealthIndex()));
        highlightBetter(tvCity1Health, tvCity2Health, selectedCity1.getHealthIndex(), selectedCity2.getHealthIndex(), true);

        // Compare AQI (lower is better)
        tvCity1AQI.setText(String.format("%.0f", selectedCity1.getAqi()));
        tvCity2AQI.setText(String.format("%.0f", selectedCity2.getAqi()));
        highlightBetter(tvCity1AQI, tvCity2AQI, selectedCity1.getAqi(), selectedCity2.getAqi(), false);

        // Compare Green Cover
        tvCity1Green.setText(String.format("%.1f%%", selectedCity1.getGreenCover()));
        tvCity2Green.setText(String.format("%.1f%%", selectedCity2.getGreenCover()));
        highlightBetter(tvCity1Green, tvCity2Green, selectedCity1.getGreenCover(), selectedCity2.getGreenCover(), true);

        // Compare Heat Stress (lower is better)
        tvCity1Heat.setText(String.format("%.1f°C", selectedCity1.getUrbanHeatIsland()));
        tvCity2Heat.setText(String.format("%.1f°C", selectedCity2.getUrbanHeatIsland()));
        highlightBetter(tvCity1Heat, tvCity2Heat, selectedCity1.getUrbanHeatIsland(), selectedCity2.getUrbanHeatIsland(), false);

        // Compare Healthcare
        tvCity1Healthcare.setText(String.valueOf(selectedCity1.getHealthcareFacilities()));
        tvCity2Healthcare.setText(String.valueOf(selectedCity2.getHealthcareFacilities()));
        highlightBetter(tvCity1Healthcare, tvCity2Healthcare, selectedCity1.getHealthcareFacilities(), selectedCity2.getHealthcareFacilities(), true);

        // Determine overall winner
        determineWinner();

        Log.d(TAG, "Comparison complete");
    }

    private void highlightBetter(TextView tv1, TextView tv2, double value1, double value2, boolean higherIsBetter) {
        tv1.setTextColor(Color.parseColor("#7f8c8d"));
        tv2.setTextColor(Color.parseColor("#7f8c8d"));
        tv1.setTextSize(18);
        tv2.setTextSize(18);

        if (higherIsBetter) {
            if (value1 > value2) {
                tv1.setTextColor(Color.parseColor("#27ae60"));
                tv1.setTextSize(22);
            } else if (value2 > value1) {
                tv2.setTextColor(Color.parseColor("#27ae60"));
                tv2.setTextSize(22);
            }
        } else {
            if (value1 < value2) {
                tv1.setTextColor(Color.parseColor("#27ae60"));
                tv1.setTextSize(22);
            } else if (value2 < value1) {
                tv2.setTextColor(Color.parseColor("#27ae60"));
                tv2.setTextSize(22);
            }
        }
    }

    private void determineWinner() {
        int city1Score = 0;
        int city2Score = 0;

        // Health Index
        if (selectedCity1.getHealthIndex() > selectedCity2.getHealthIndex()) city1Score++;
        else if (selectedCity2.getHealthIndex() > selectedCity1.getHealthIndex()) city2Score++;

        // AQI (lower is better)
        if (selectedCity1.getAqi() < selectedCity2.getAqi()) city1Score++;
        else if (selectedCity2.getAqi() < selectedCity1.getAqi()) city2Score++;

        // Green Cover
        if (selectedCity1.getGreenCover() > selectedCity2.getGreenCover()) city1Score++;
        else if (selectedCity2.getGreenCover() > selectedCity1.getGreenCover()) city2Score++;

        // Heat (lower is better)
        if (selectedCity1.getUrbanHeatIsland() < selectedCity2.getUrbanHeatIsland()) city1Score++;
        else if (selectedCity2.getUrbanHeatIsland() < selectedCity1.getUrbanHeatIsland()) city2Score++;

        // Healthcare
        if (selectedCity1.getHealthcareFacilities() > selectedCity2.getHealthcareFacilities()) city1Score++;
        else if (selectedCity2.getHealthcareFacilities() > selectedCity1.getHealthcareFacilities()) city2Score++;

        Log.d(TAG, "City1 score: " + city1Score + ", City2 score: " + city2Score);

        if (city1Score > city2Score) {
            tvWinner.setText("🏆 " + selectedCity1.getCityName() + " is Healthier!");
            tvWinner.setTextColor(Color.parseColor("#27ae60"));
        } else if (city2Score > city1Score) {
            tvWinner.setText("🏆 " + selectedCity2.getCityName() + " is Healthier!");
            tvWinner.setTextColor(Color.parseColor("#27ae60"));
        } else {
            tvWinner.setText("⚖️ Both cities are equally healthy!");
            tvWinner.setTextColor(Color.parseColor("#3498db"));
        }
    }
}