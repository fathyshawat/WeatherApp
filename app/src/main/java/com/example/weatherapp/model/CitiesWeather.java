package com.example.weatherapp.model;

public class CitiesWeather {

    private String name;
    private double minTemp;
    private double maxTemp;
    private String description;
    private double windSpeed;
    private String icon;

    public CitiesWeather(String name, String icon, double minTemp, double maxTemp,
                         String description, double windSpeed) {
        this.name = name;
        this.icon = icon;
        this.minTemp = minTemp;
        this.maxTemp = maxTemp;
        this.description = description;
        this.windSpeed = windSpeed;
    }

    public double getMinTemp() {
        return minTemp ;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public String getDescription() {
        return description;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
}
