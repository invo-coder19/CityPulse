package com.example.cityhealth;


import java.io.Serializable;

public class City implements Serializable {
    private String cityName;
    private String state;
    private double healthIndex;
    private double aqi;
    private double greenCover;
    private double urbanHeatIsland;
    private double waterFloodRisk;
    private int healthcareFacilities;

    // Constructor
    public City(String cityName, String state, double healthIndex, double aqi,
                double greenCover, double urbanHeatIsland, double waterFloodRisk,
                int healthcareFacilities) {
        this.cityName = cityName;
        this.state = state;
        this.healthIndex = healthIndex;
        this.aqi = aqi;
        this.greenCover = greenCover;
        this.urbanHeatIsland = urbanHeatIsland;
        this.waterFloodRisk = waterFloodRisk;
        this.healthcareFacilities = healthcareFacilities;
    }

    // Getters
    public String getCityName() {
        return cityName;
    }

    public String getState() {
        return state;
    }

    public double getHealthIndex() {
        return healthIndex;
    }

    public double getAqi() {
        return aqi;
    }

    public double getGreenCover() {
        return greenCover;
    }

    public double getUrbanHeatIsland() {
        return urbanHeatIsland;
    }

    public double getWaterFloodRisk() {
        return waterFloodRisk;
    }

    public int getHealthcareFacilities() {
        return healthcareFacilities;
    }

    // Setters
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setHealthIndex(double healthIndex) {
        this.healthIndex = healthIndex;
    }

    public void setAqi(double aqi) {
        this.aqi = aqi;
    }

    public void setGreenCover(double greenCover) {
        this.greenCover = greenCover;
    }

    public void setUrbanHeatIsland(double urbanHeatIsland) {
        this.urbanHeatIsland = urbanHeatIsland;
    }

    public void setWaterFloodRisk(double waterFloodRisk) {
        this.waterFloodRisk = waterFloodRisk;
    }

    public void setHealthcareFacilities(int healthcareFacilities) {
        this.healthcareFacilities = healthcareFacilities;
    }

    // Helper method to get AQI status
    public String getAqiStatus() {
        if (aqi <= 50) return "Good";
        else if (aqi <= 100) return "Moderate";
        else if (aqi <= 150) return "Unhealthy for Sensitive Groups";
        else if (aqi <= 200) return "Unhealthy";
        else if (aqi <= 300) return "Very Unhealthy";
        else return "Hazardous";
    }

    // Helper method to get AQI color
    public String getAqiColor() {
        if (aqi <= 50) return "#27ae60";
        else if (aqi <= 100) return "#f39c12";
        else if (aqi <= 150) return "#e67e22";
        else if (aqi <= 200) return "#e74c3c";
        else if (aqi <= 300) return "#8e44ad";
        else return "#c0392b";
    }

    // Helper method to get health index status
    public String getHealthStatus() {
        if (healthIndex >= 80) return "Excellent";
        else if (healthIndex >= 60) return "Good";
        else if (healthIndex >= 40) return "Moderate";
        else if (healthIndex >= 20) return "Poor";
        else return "Critical";
    }
}
