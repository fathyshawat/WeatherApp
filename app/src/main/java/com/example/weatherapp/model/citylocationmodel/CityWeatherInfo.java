package com.example.weatherapp.model.citylocationmodel;

public class CityWeatherInfo {

    private double minTemp;
    private double maxTemp;
    private double wind;

    private String icon;
    private String date;
    private String description;

    public CityWeatherInfo(double minTemp, double maxTemp, double wind,
                           String icon, String description,
                           String date) {
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.icon = icon;
        this.date = date;
        this.wind = wind;
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public double getMinTemp() {
        return minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public double getWind() {
        return wind;
    }

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }
}
