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
        if (currentUser.isEmpty()) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        String userName = sharedPreferences.getString(currentUser + "_name", "User");
        tvWelcome.setText("Welcome, " + userName + "!");

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
                String userCityName = sharedPreferences.getString(currentUser + "_city", "");
                City targetCity = null;
                if (!userCityName.isEmpty() && cityList != null) {
                    for (City c : cityList) {
                        if (c.getCityName().equalsIgnoreCase(userCityName.trim())) {
                            targetCity = c;
                            break;
                        }
                    }
                }
                if (targetCity == null && cityList != null && !cityList.isEmpty()) {
                    targetCity = cityList.get(0);
                }

                if (targetCity != null) {
                    Intent intent = new Intent(HomeActivity.this, DashboardActivity.class);
                    intent.putExtra("CITY_DATA", targetCity);
                    startActivity(intent);
                } else {
                    Toast.makeText(HomeActivity.this, "Please select a city from the list below", Toast.LENGTH_SHORT).show();
                }
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
            }
        } catch (Exception e) {
            cityList = csvReader.getSampleCities();
        }
    }

    private void setupRecyclerViews() {
        if (cityList == null || cityList.isEmpty()) {
            return;
        }

        // Search Results RecyclerView
        rvSearchResults.setLayoutManager(new LinearLayoutManager(this));
        searchAdapter = new CityAdapter(this, cityList, false);
        rvSearchResults.setAdapter(searchAdapter);
        rvSearchResults.setVisibility(View.GONE);

        // Featured Cities RecyclerView (Horizontal)
        rvFeaturedCities.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<City> featuredSubList = cityList.subList(0, Math.min(5, cityList.size()));
        featuredAdapter = new CityAdapter(this, new ArrayList<>(featuredSubList), true);
        rvFeaturedCities.setAdapter(featuredAdapter);

        // Available Cities RecyclerView (Vertical)
        rvAvailableCities.setLayoutManager(new LinearLayoutManager(this));
        availableAdapter = new CityAdapter(this, cityList, false);
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