package com.example.cityhealth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputEditText;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private TextInputEditText etSearchCity;
    private Button btnLogout, btnDashboard, btnCompare, btnProfile, btnSettings;
    private RecyclerView rvSearchResults, rvFeaturedCities, rvAvailableCities;
    private SharedPreferences sharedPreferences;
    private CityAdapter searchAdapter;
    private CityAdapter featuredAdapter;
    private CityAdapter availableAdapter;
    private List<City> cityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize views
        tvWelcome = findViewById(R.id.tvWelcome);
        etSearchCity = findViewById(R.id.etSearchCity);
        btnLogout = findViewById(R.id.btnLogout);
        btnDashboard = findViewById(R.id.btnDashboard);
        btnCompare = findViewById(R.id.btnCompare);
        btnProfile = findViewById(R.id.btnProfile);
        btnSettings = findViewById(R.id.btnSettings);
        rvSearchResults = findViewById(R.id.rvSearchResults);
        rvFeaturedCities = findViewById(R.id.rvFeaturedCities);
        rvAvailableCities = findViewById(R.id.rvAvailableCities);

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);

        // Get current user and display welcome message
        String currentUser = sharedPreferences.getString("current_user", "");
        String userName = sharedPreferences.getString(currentUser + "_name", "User");
        tvWelcome.setText("Welcome " + userName + "!");

        // Load city data from CSV
        loadCityData();

        // Setup RecyclerViews
        setupRecyclerViews();

        // Setup search functionality
        setupSearch();

        // Logout button click listener
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove("current_user");
                editor.apply();

                Toast.makeText(HomeActivity.this, "Logged out successfully!", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Bottom navigation button listeners
        btnDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Please search and select a city first", Toast.LENGTH_SHORT).show();
            }
        });

        btnCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CompareActivity.class);
                startActivity(intent);
            }
        });

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                startActivity(intent);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void loadCityData() {
        CSVReader csvReader = new CSVReader(this);

        try {
            cityList = csvReader.readCitiesFromCSV("cities_data.csv");
            if (cityList.isEmpty()) {
                cityList = csvReader.getSampleCities();
                Toast.makeText(this, "Using sample data. Add cities_data.csv to assets folder", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "City Data Loaded", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            cityList = csvReader.getSampleCities();
            Toast.makeText(this, "CSV not found. Using sample data", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupRecyclerViews() {
        // Search Results RecyclerView
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new CityAdapter(this, cityList);
        rvSearchResults.setAdapter(searchAdapter);
        rvSearchResults.setVisibility(View.GONE);

        // Featured Cities RecyclerView (Horizontal)
        rvFeaturedCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        featuredAdapter = new CityAdapter(this, cityList.subList(0, Math.min(5, cityList.size())));
        rvFeaturedCities.setAdapter(featuredAdapter);

        // Available Cities RecyclerView (Vertical)
        rvAvailableCities.setLayoutManager(new LinearLayoutManager(this));
        availableAdapter = new CityAdapter(this, cityList);
        rvAvailableCities.setAdapter(availableAdapter);
    }

    private void setupSearch() {
        etSearchCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchAdapter.filter(s.toString());

                if (s.length() > 0) {
                    rvSearchResults.setVisibility(View.VISIBLE);
                    rvFeaturedCities.setVisibility(View.GONE);
                    rvAvailableCities.setVisibility(View.GONE);
                } else {
                    rvSearchResults.setVisibility(View.GONE);
                    rvFeaturedCities.setVisibility(View.VISIBLE);
                    rvAvailableCities.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}