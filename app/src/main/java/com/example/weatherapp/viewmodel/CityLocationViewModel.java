package com.example.weatherapp.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.weatherapp.model.citylocationmodel.CitiesLocationWeather;
import com.example.weatherapp.repository.CityLocationRepository;
import com.example.weatherapp.utils.Resource;


import javax.inject.Inject;

public class CityLocationViewModel extends ViewModel {

    private CityLocationRepository cityLocationRepository;
    private MutableLiveData<Resource<CitiesLocationWeather>> cityLocationLiveData = new MutableLiveData<>();

    @Inject
    CityLocationViewModel(CityLocationRepository multiCitiesRepository) {
        this.cityLocationRepository = multiCitiesRepository;
    }

    public void getCityWeather(String lat, String lng) {
        cityLocationLiveData = cityLocationRepository.getCityLocationWeather(lat, lng);
    }


    public MutableLiveData<Resource<CitiesLocationWeather>> getCityLocationLiveData() {
        return cityLocationLiveData;
    }

}
