package com.example.weatherapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.CitiesWeather;

import com.example.weatherapp.repository.MultiCitiesRepository;
import com.example.weatherapp.utils.Resource;


import java.util.List;

import javax.inject.Inject;

public class MultiCitiesViewModel extends ViewModel {


    private MultiCitiesRepository multiCitiesRepository;
    private MutableLiveData<Resource<List<CitiesWeather>>> citiesWeatherLiveData = new MutableLiveData<>();

    @Inject
    MultiCitiesViewModel(MultiCitiesRepository multiCitiesRepository) {
        this.multiCitiesRepository = multiCitiesRepository;
    }

    public void getCities(String cities) {
        citiesWeatherLiveData = multiCitiesRepository.getMultiCities(cities);
    }

    public MutableLiveData<Resource<List<CitiesWeather>>> getCitiesWeatherLiveData() {
        return citiesWeatherLiveData;
    }
}
