package com.example.cityhealth;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    private Context context;

    public CSVReader(Context context) {
        this.context = context;
    }

    /**
     * Reads city data from CSV file in assets folder
     * Expected CSV format:
     * CityName,State,HealthIndex,AQI,GreenCover,UrbanHeatIsland,WaterFloodRisk,HealthcareFacilities
     */
    public List<City> readCitiesFromCSV(String fileName) {
        List<City> cities = new ArrayList<>();

        try {
            InputStream inputStream = context.getAssets().open(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            boolean firstLine = true;

            while ((line = reader.readLine()) != null) {
                // Skip header line
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                // Parse CSV line
                String[] values = line.split(",");

                if (values.length >= 8) {
                    try {
                        String cityName = values[0].trim();
                        String state = values[1].trim();
                        double healthIndex = Double.parseDouble(values[2].trim());
                        double aqi = Double.parseDouble(values[3].trim());
                        double greenCover = Double.parseDouble(values[4].trim());
                        double urbanHeatIsland = Double.parseDouble(values[5].trim());
                        double waterFloodRisk = Double.parseDouble(values[6].trim());
                        int healthcareFacilities = Integer.parseInt(values[7].trim());

                        City city = new City(cityName, state, healthIndex, aqi,
                                greenCover, urbanHeatIsland, waterFloodRisk,
                                healthcareFacilities);
                        cities.add(city);

                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return cities;
    }

    /**
     * Get sample data if CSV is not available
     */
    public List<City> getSampleCities() {
        List<City> cities = new ArrayList<>();

        cities.add(new City("Mumbai", "Maharashtra", 75.5, 120.0, 28.5, 42.5, 65.0, 450));
        cities.add(new City("Delhi", "Delhi", 62.3, 185.0, 22.0, 45.8, 55.0, 380));
        cities.add(new City("Bangalore", "Karnataka", 81.2, 95.0, 45.6, 38.2, 35.0, 520));
        cities.add(new City("Pune", "Maharashtra", 78.5, 110.0, 38.5, 40.2, 45.0, 340));
        cities.add(new City("Chennai", "Tamil Nadu", 72.8, 115.0, 32.5, 44.5, 58.0, 410));
        cities.add(new City("Hyderabad", "Telangana", 76.3, 105.0, 35.8, 41.5, 48.0, 390));
        cities.add(new City("Kolkata", "West Bengal", 68.5, 145.0, 30.2, 43.8, 72.0, 360));
        cities.add(new City("Ahmedabad", "Gujarat", 70.2, 135.0, 25.5, 46.2, 42.0, 310));
        cities.add(new City("Jaipur", "Rajasthan", 71.5, 128.0, 27.8, 47.5, 38.0, 280));
        cities.add(new City("Lucknow", "Uttar Pradesh", 65.8, 155.0, 26.5, 44.2, 52.0, 290));

        return cities;
    }
}
