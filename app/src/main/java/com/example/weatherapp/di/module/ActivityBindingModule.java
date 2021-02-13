package com.example.weatherapp.di.module;

import com.example.weatherapp.view.WeatherCitiesActivity;
import com.example.weatherapp.view.WeatherCityLocationActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBindingModule {

    @ContributesAndroidInjector
    abstract WeatherCitiesActivity bindMainActivity();

    @ContributesAndroidInjector
    abstract WeatherCityLocationActivity bindCityLocationActivity();
}