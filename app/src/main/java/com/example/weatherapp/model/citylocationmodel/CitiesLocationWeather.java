package com.example.weatherapp.model.citylocationmodel;

import java.util.List;

public class CitiesLocationWeather {

    private String city;
    private String country;

    private List<CityWeatherInfo> cityWeatherInfoLs;

    public CitiesLocationWeather(String city, String country, List<CityWeatherInfo> cityWeatherInfoLs) {
        this.city = city;
        this.country = country;
        this.cityWeatherInfoLs = cityWeatherInfoLs;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    public List<CityWeatherInfo> getCityWeatherInfoLs() {
        return cityWeatherInfoLs;
    }
}
